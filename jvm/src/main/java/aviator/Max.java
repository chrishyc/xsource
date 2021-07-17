package aviator;

import com.beust.jcommander.internal.Lists;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Max extends AbstractFunction {
    public static void main(String[] args) {
        // 注册函数`IF`
        AviatorEvaluator.addFunction(new Max());
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
        Map<String, Object> map = new HashMap<>();
        map.put("data", list);
        map.put("c",33);
        Object execute1 = AviatorEvaluator.execute("Max(a,b)<=c", map, true);
        System.out.println(execute1);
    }
    
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
        List<Integer> data = (List<Integer>) env.get("data");
        return new AviatorDouble(data.stream().mapToInt(Integer::intValue).max().getAsInt());
    }
    
    @Override
    public String getName() {
        return "Max";
    }
}
