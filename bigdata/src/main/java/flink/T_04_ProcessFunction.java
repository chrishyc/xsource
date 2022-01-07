package flink;

import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.walkthrough.common.entity.Alert;
import org.apache.flink.walkthrough.common.entity.Transaction;

public class T_04_ProcessFunction extends KeyedProcessFunction<Long, Transaction, Alert> {
  private static final long serialVersionUID = 1L;

  private static final double SMALL_AMOUNT = 1.00;
  private static final double LARGE_AMOUNT = 500.00;
  private static final long ONE_MINUTE = 60 * 1000;
  private transient ValueState<Boolean> flagState;

  @Override
  public void open(Configuration parameters) {
    ValueStateDescriptor<Boolean> flagDescriptor = new ValueStateDescriptor<>(
        "flag",
        Types.BOOLEAN);
    flagState = getRuntimeContext().getState(flagDescriptor);
  }


  @Override
  public void processElement(Transaction value, Context ctx, Collector<Alert> out) throws Exception {
    Alert alert = new Alert();
    alert.setId(value.getAccountId());
    out.collect(alert);
    flagState.update(false);
  }
}
