package spring.springboot.pojo;


import com.xiaomi.mifi.desensitize.agent.annotation.DesensitizeField;
import com.xiaomi.mifi.desensitize.agent.annotation.Desensitized;
import com.xiaomi.mifi.desensitize.agent.enums.DesensitizedFilterEnum;
import com.xiaomi.mifi.desensitize.agent.filter.FilterFactory;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Desensitized
public class CreditResult
        implements Serializable {
    private int creditScene;
    private double riskScore;
    private double incomeScore;
    private int riskLevel;
    private int incomeLevel;
    private long cashAmount;
    private long installmentAmount;
    private int amountVersion;
    private String processVersion;
    private int cashRate;
    private int cashRateVersion;
    private int riskCashRate = -9999;
    private int discountCashRate = -9999;
    private int nominalCashRate;
    private int nominalCashRateVersion;
    private int installmentRate;
    private int installmentRateVersion;
    private int feeRate1;
    private int feeRate3;
    private int feeRate6;
    private int feeRate12;
    private int feeRate24;
    private int userLevel;
    private int userLevelVersion;
    private int userChannel;
    private int userProvider;
    private int amountStatus;
    private int status;
    private String amountSource;
    private int creditAction;
    private int refuseReason;
    private boolean adjusted;
    private int nextStage;
    private List<Integer> terms;
    private int cashFinalStatusYsx;
    private int cashFinalStatusSqz;
    private int amountMode;
    @DesensitizeField(value=DesensitizedFilterEnum.ABSTRACT_MAP)
    private Map<String, Object> varMap;
    private long instalmentOverdraftAmount;
    private long orderTypeStandard;
    private int overdraftProportionA;
    private int overdraftProportionB;
    
    protected boolean canEqual(Object other) {
        /* 18*/         return other instanceof CreditResult;
    }
    
    public void setTerms(List<Integer> terms) {
        /* 18*/         this.terms = terms;
    }
    
    public List<Integer> getTerms() {
        /*162*/         return this.terms;
    }
    
    public int getCreditScene() {
        /* 26*/         return this.creditScene;
    }
    
    public double getRiskScore() {
        /* 30*/         return this.riskScore;
    }
    
    public double getIncomeScore() {
        /* 34*/         return this.incomeScore;
    }
    
    public int getRiskLevel() {
        /* 38*/         return this.riskLevel;
    }
    
    public int getIncomeLevel() {
        /* 42*/         return this.incomeLevel;
    }
    
    public long getCashAmount() {
        /* 46*/         return this.cashAmount;
    }
    
    public long getInstallmentAmount() {
        /* 50*/         return this.installmentAmount;
    }
    
    public int getAmountVersion() {
        /* 54*/         return this.amountVersion;
    }
    
    public String getProcessVersion() {
        /* 58*/         return this.processVersion;
    }
    
    public int getCashRate() {
        /* 62*/         return this.cashRate;
    }
    
    public int getCashRateVersion() {
        /* 66*/         return this.cashRateVersion;
    }
    
    public int getRiskCashRate() {
        /* 71*/         return this.riskCashRate;
    }
    
    public int getDiscountCashRate() {
        /* 76*/         return this.discountCashRate;
    }
    
    public int getNominalCashRate() {
        /* 81*/         return this.nominalCashRate;
    }
    
    public int getNominalCashRateVersion() {
        /* 85*/         return this.nominalCashRateVersion;
    }
    
    public int getInstallmentRate() {
        /* 90*/         return this.installmentRate;
    }
    
    public int getInstallmentRateVersion() {
        /* 94*/         return this.installmentRateVersion;
    }
    
    public int getFeeRate1() {
        /* 98*/         return this.feeRate1;
    }
    
    public int getFeeRate3() {
        /* 99*/         return this.feeRate3;
    }
    
    public int getFeeRate6() {
        /*100*/         return this.feeRate6;
    }
    
    public int getFeeRate12() {
        /*101*/         return this.feeRate12;
    }
    
    public int getFeeRate24() {
        /*106*/         return this.feeRate24;
    }
    
    public int getUserLevel() {
        /*110*/         return this.userLevel;
    }
    
    public int getUserLevelVersion() {
        /*114*/         return this.userLevelVersion;
    }
    
    public int getUserChannel() {
        /*118*/         return this.userChannel;
    }
    
    public int getUserProvider() {
        /*124*/         return this.userProvider;
    }
    
    public int getAmountStatus() {
        /*129*/         return this.amountStatus;
    }
    
    public int getStatus() {
        /*134*/         return this.status;
    }
    
    public String getAmountSource() {
        /*139*/         return this.amountSource;
    }
    
    public int getCreditAction() {
        /*144*/         return this.creditAction;
    }
    
    public int getRefuseReason() {
        /*149*/         return this.refuseReason;
    }
    
    public boolean isAdjusted() {
        /*154*/         return this.adjusted;
    }
    
    public int getNextStage() {
        /*160*/         return this.nextStage;
    }
    
    public int getCashFinalStatusYsx() {
        /*167*/         return this.cashFinalStatusYsx;
    }
    
    public int getCashFinalStatusSqz() {
        /*171*/         return this.cashFinalStatusSqz;
    }
    
    public int getAmountMode() {
        /*179*/         return this.amountMode;
    }
    
    public Map<String, Object> getVarMap() {
        /*181*/         return this.varMap;
    }
    
    public long getInstalmentOverdraftAmount() {
        /*186*/         return this.instalmentOverdraftAmount;
    }
    
    public long getOrderTypeStandard() {
        /*191*/         return this.orderTypeStandard;
    }
    
    public int getOverdraftProportionA() {
        /*196*/         return this.overdraftProportionA;
    }
    
    public int getOverdraftProportionB() {
        /*202*/         return this.overdraftProportionB;
    }
    
    public void setCreditScene(int creditScene) {
        /* 18*/         this.creditScene = creditScene;
    }
    
    public void setRiskScore(double riskScore) {
        /* 18*/         this.riskScore = riskScore;
    }
    
    public void setIncomeScore(double incomeScore) {
        /* 18*/         this.incomeScore = incomeScore;
    }
    
    public void setRiskLevel(int riskLevel) {
        /* 18*/         this.riskLevel = riskLevel;
    }
    
    public void setIncomeLevel(int incomeLevel) {
        /* 18*/         this.incomeLevel = incomeLevel;
    }
    
    public void setCashAmount(long cashAmount) {
        /* 18*/         this.cashAmount = cashAmount;
    }
    
    public void setInstallmentAmount(long installmentAmount) {
        /* 18*/         this.installmentAmount = installmentAmount;
    }
    
    public void setAmountVersion(int amountVersion) {
        /* 18*/         this.amountVersion = amountVersion;
    }
    
    public void setProcessVersion(String processVersion) {
        /* 18*/         this.processVersion = processVersion;
    }
    
    public void setCashRate(int cashRate) {
        /* 18*/         this.cashRate = cashRate;
    }
    
    public void setCashRateVersion(int cashRateVersion) {
        /* 18*/         this.cashRateVersion = cashRateVersion;
    }
    
    public void setRiskCashRate(int riskCashRate) {
        /* 18*/         this.riskCashRate = riskCashRate;
    }
    
    public void setDiscountCashRate(int discountCashRate) {
        /* 18*/         this.discountCashRate = discountCashRate;
    }
    
    public void setNominalCashRate(int nominalCashRate) {
        /* 18*/         this.nominalCashRate = nominalCashRate;
    }
    
    public void setNominalCashRateVersion(int nominalCashRateVersion) {
        /* 18*/         this.nominalCashRateVersion = nominalCashRateVersion;
    }
    
    public void setInstallmentRate(int installmentRate) {
        /* 18*/         this.installmentRate = installmentRate;
    }
    
    public void setInstallmentRateVersion(int installmentRateVersion) {
        /* 18*/         this.installmentRateVersion = installmentRateVersion;
    }
    
    public void setFeeRate1(int feeRate1) {
        /* 18*/         this.feeRate1 = feeRate1;
    }
    
    public void setFeeRate3(int feeRate3) {
        /* 18*/         this.feeRate3 = feeRate3;
    }
    
    public void setFeeRate6(int feeRate6) {
        /* 18*/         this.feeRate6 = feeRate6;
    }
    
    public void setFeeRate12(int feeRate12) {
        /* 18*/         this.feeRate12 = feeRate12;
    }
    
    public void setFeeRate24(int feeRate24) {
        /* 18*/         this.feeRate24 = feeRate24;
    }
    
    public void setUserLevel(int userLevel) {
        /* 18*/         this.userLevel = userLevel;
    }
    
    public void setUserLevelVersion(int userLevelVersion) {
        /* 18*/         this.userLevelVersion = userLevelVersion;
    }
    
    public void setUserChannel(int userChannel) {
        /* 18*/         this.userChannel = userChannel;
    }
    
    public void setUserProvider(int userProvider) {
        /* 18*/         this.userProvider = userProvider;
    }
    
    public void setAmountStatus(int amountStatus) {
        /* 18*/         this.amountStatus = amountStatus;
    }
    
    public void setStatus(int status) {
        /* 18*/         this.status = status;
    }
    
    public void setAmountSource(String amountSource) {
        /* 18*/         this.amountSource = amountSource;
    }
    
    public void setCreditAction(int creditAction) {
        /* 18*/         this.creditAction = creditAction;
    }
    
    public void setRefuseReason(int refuseReason) {
        /* 18*/         this.refuseReason = refuseReason;
    }
    
    public void setAdjusted(boolean adjusted) {
        /* 18*/         this.adjusted = adjusted;
    }
    
    public void setNextStage(int nextStage) {
        /* 18*/         this.nextStage = nextStage;
    }
    
    public void setCashFinalStatusYsx(int cashFinalStatusYsx) {
        /* 18*/         this.cashFinalStatusYsx = cashFinalStatusYsx;
    }
    
    public void setCashFinalStatusSqz(int cashFinalStatusSqz) {
        /* 18*/         this.cashFinalStatusSqz = cashFinalStatusSqz;
    }
    
    public void setAmountMode(int amountMode) {
        /* 18*/         this.amountMode = amountMode;
    }
    
    public void setVarMap(Map<String, Object> varMap) {
        /* 18*/         this.varMap = varMap;
    }
    
    public void setInstalmentOverdraftAmount(long instalmentOverdraftAmount) {
        /* 18*/         this.instalmentOverdraftAmount = instalmentOverdraftAmount;
    }
    
    public void setOrderTypeStandard(long orderTypeStandard) {
        /* 18*/         this.orderTypeStandard = orderTypeStandard;
    }
    
    public void setOverdraftProportionA(int overdraftProportionA) {
        /* 18*/         this.overdraftProportionA = overdraftProportionA;
    }
    
    public void setOverdraftProportionB(int overdraftProportionB) {
        /* 18*/         this.overdraftProportionB = overdraftProportionB;
    }
    
    public boolean equals(Object o) {
        /* 18*/         if (o == this) {
            return true;
        }
        if (!(o instanceof CreditResult)) {
            return false;
        }
        CreditResult other = (CreditResult)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getCreditScene() != other.getCreditScene()) {
            return false;
        }
        if (Double.compare(this.getRiskScore(), other.getRiskScore()) != 0) {
            return false;
        }
        if (Double.compare(this.getIncomeScore(), other.getIncomeScore()) != 0) {
            return false;
        }
        if (this.getRiskLevel() != other.getRiskLevel()) {
            return false;
        }
        if (this.getIncomeLevel() != other.getIncomeLevel()) {
            return false;
        }
        if (this.getCashAmount() != other.getCashAmount()) {
            return false;
        }
        if (this.getInstallmentAmount() != other.getInstallmentAmount()) {
            return false;
        }
        if (this.getAmountVersion() != other.getAmountVersion()) {
            return false;
        }
        String this$processVersion = this.getProcessVersion();
        String other$processVersion = other.getProcessVersion();
        if (this$processVersion == null ? other$processVersion != null : !this$processVersion.equals(other$processVersion)) {
            return false;
        }
        if (this.getCashRate() != other.getCashRate()) {
            return false;
        }
        if (this.getCashRateVersion() != other.getCashRateVersion()) {
            return false;
        }
        if (this.getRiskCashRate() != other.getRiskCashRate()) {
            return false;
        }
        if (this.getDiscountCashRate() != other.getDiscountCashRate()) {
            return false;
        }
        if (this.getNominalCashRate() != other.getNominalCashRate()) {
            return false;
        }
        if (this.getNominalCashRateVersion() != other.getNominalCashRateVersion()) {
            return false;
        }
        if (this.getInstallmentRate() != other.getInstallmentRate()) {
            return false;
        }
        if (this.getInstallmentRateVersion() != other.getInstallmentRateVersion()) {
            return false;
        }
        if (this.getFeeRate1() != other.getFeeRate1()) {
            return false;
        }
        if (this.getFeeRate3() != other.getFeeRate3()) {
            return false;
        }
        if (this.getFeeRate6() != other.getFeeRate6()) {
            return false;
        }
        if (this.getFeeRate12() != other.getFeeRate12()) {
            return false;
        }
        if (this.getFeeRate24() != other.getFeeRate24()) {
            return false;
        }
        if (this.getUserLevel() != other.getUserLevel()) {
            return false;
        }
        if (this.getUserLevelVersion() != other.getUserLevelVersion()) {
            return false;
        }
        if (this.getUserChannel() != other.getUserChannel()) {
            return false;
        }
        if (this.getUserProvider() != other.getUserProvider()) {
            return false;
        }
        if (this.getAmountStatus() != other.getAmountStatus()) {
            return false;
        }
        if (this.getStatus() != other.getStatus()) {
            return false;
        }
        String this$amountSource = this.getAmountSource();
        String other$amountSource = other.getAmountSource();
        if (this$amountSource == null ? other$amountSource != null : !this$amountSource.equals(other$amountSource)) {
            return false;
        }
        if (this.getCreditAction() != other.getCreditAction()) {
            return false;
        }
        if (this.getRefuseReason() != other.getRefuseReason()) {
            return false;
        }
        if (this.isAdjusted() != other.isAdjusted()) {
            return false;
        }
        if (this.getNextStage() != other.getNextStage()) {
            return false;
        }
        List<Integer> this$terms = this.getTerms();
        List<Integer> other$terms = other.getTerms();
        if (this$terms == null ? other$terms != null : !((Object)this$terms).equals(other$terms)) {
            return false;
        }
        if (this.getCashFinalStatusYsx() != other.getCashFinalStatusYsx()) {
            return false;
        }
        if (this.getCashFinalStatusSqz() != other.getCashFinalStatusSqz()) {
            return false;
        }
        if (this.getAmountMode() != other.getAmountMode()) {
            return false;
        }
        Map<String, Object> this$varMap = this.getVarMap();
        Map<String, Object> other$varMap = other.getVarMap();
        if (this$varMap == null ? other$varMap != null : !((Object)this$varMap).equals(other$varMap)) {
            return false;
        }
        if (this.getInstalmentOverdraftAmount() != other.getInstalmentOverdraftAmount()) {
            return false;
        }
        if (this.getOrderTypeStandard() != other.getOrderTypeStandard()) {
            return false;
        }
        if (this.getOverdraftProportionA() != other.getOverdraftProportionA()) {
            return false;
        }
        return this.getOverdraftProportionB() == other.getOverdraftProportionB();
    }
    
    public String toString() {
        return "CreditResult(creditScene=" + this.creditScene + ", riskScore=" + this.riskScore + ", incomeScore=" + this.incomeScore + ", riskLevel=" + this.riskLevel + ", incomeLevel=" + this.incomeLevel + ", cashAmount=" + this.cashAmount + ", installmentAmount=" + this.installmentAmount + ", amountVersion=" + this.amountVersion + ", processVersion=" + this.processVersion + ", cashRate=" + this.cashRate + ", cashRateVersion=" + this.cashRateVersion + ", riskCashRate=" + this.riskCashRate + ", discountCashRate=" + this.discountCashRate + ", nominalCashRate=" + this.nominalCashRate + ", nominalCashRateVersion=" + this.nominalCashRateVersion + ", installmentRate=" + this.installmentRate + ", installmentRateVersion=" + this.installmentRateVersion + ", feeRate1=" + this.feeRate1 + ", feeRate3=" + this.feeRate3 + ", feeRate6=" + this.feeRate6 + ", feeRate12=" + this.feeRate12 + ", feeRate24=" + this.feeRate24 + ", userLevel=" + this.userLevel + ", userLevelVersion=" + this.userLevelVersion + ", userChannel=" + this.userChannel + ", userProvider=" + this.userProvider + ", amountStatus=" + this.amountStatus + ", status=" + this.status + ", amountSource=" + this.amountSource + ", creditAction=" + this.creditAction + ", refuseReason=" + this.refuseReason + ", adjusted=" + this.adjusted + ", nextStage=" + this.nextStage + ", terms=" + this.terms + ", cashFinalStatusYsx=" + this.cashFinalStatusYsx + ", cashFinalStatusSqz=" + this.cashFinalStatusSqz + ", amountMode=" + this.amountMode + ", varMap=" + String.valueOf(FilterFactory.getFilterInstance("ABSTRACT_MAP").desensitize(this.varMap)) + ", instalmentOverdraftAmount=" + this.instalmentOverdraftAmount + ", orderTypeStandard=" + this.orderTypeStandard + ", overdraftProportionA=" + this.overdraftProportionA + ", overdraftProportionB=" + this.overdraftProportionB + ")";
    }
    
    public int hashCode() {
        /* 18*/         int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getCreditScene();
        long $riskScore = Double.doubleToLongBits(this.getRiskScore());
        result = result * 59 + (int)($riskScore >>> 32 ^ $riskScore);
        long $incomeScore = Double.doubleToLongBits(this.getIncomeScore());
        result = result * 59 + (int)($incomeScore >>> 32 ^ $incomeScore);
        result = result * 59 + this.getRiskLevel();
        result = result * 59 + this.getIncomeLevel();
        long $cashAmount = this.getCashAmount();
        result = result * 59 + (int)($cashAmount >>> 32 ^ $cashAmount);
        long $installmentAmount = this.getInstallmentAmount();
        result = result * 59 + (int)($installmentAmount >>> 32 ^ $installmentAmount);
        result = result * 59 + this.getAmountVersion();
        String $processVersion = this.getProcessVersion();
        result = result * 59 + ($processVersion == null ? 43 : $processVersion.hashCode());
        result = result * 59 + this.getCashRate();
        result = result * 59 + this.getCashRateVersion();
        result = result * 59 + this.getRiskCashRate();
        result = result * 59 + this.getDiscountCashRate();
        result = result * 59 + this.getNominalCashRate();
        result = result * 59 + this.getNominalCashRateVersion();
        result = result * 59 + this.getInstallmentRate();
        result = result * 59 + this.getInstallmentRateVersion();
        result = result * 59 + this.getFeeRate1();
        result = result * 59 + this.getFeeRate3();
        result = result * 59 + this.getFeeRate6();
        result = result * 59 + this.getFeeRate12();
        result = result * 59 + this.getFeeRate24();
        result = result * 59 + this.getUserLevel();
        result = result * 59 + this.getUserLevelVersion();
        result = result * 59 + this.getUserChannel();
        result = result * 59 + this.getUserProvider();
        result = result * 59 + this.getAmountStatus();
        result = result * 59 + this.getStatus();
        String $amountSource = this.getAmountSource();
        result = result * 59 + ($amountSource == null ? 43 : $amountSource.hashCode());
        result = result * 59 + this.getCreditAction();
        result = result * 59 + this.getRefuseReason();
        result = result * 59 + (this.isAdjusted() ? 79 : 97);
        result = result * 59 + this.getNextStage();
        List<Integer> $terms = this.getTerms();
        result = result * 59 + ($terms == null ? 43 : ((Object)$terms).hashCode());
        result = result * 59 + this.getCashFinalStatusYsx();
        result = result * 59 + this.getCashFinalStatusSqz();
        result = result * 59 + this.getAmountMode();
        Map<String, Object> $varMap = this.getVarMap();
        result = result * 59 + ($varMap == null ? 43 : ((Object)$varMap).hashCode());
        long $instalmentOverdraftAmount = this.getInstalmentOverdraftAmount();
        result = result * 59 + (int)($instalmentOverdraftAmount >>> 32 ^ $instalmentOverdraftAmount);
        long $orderTypeStandard = this.getOrderTypeStandard();
        result = result * 59 + (int)($orderTypeStandard >>> 32 ^ $orderTypeStandard);
        result = result * 59 + this.getOverdraftProportionA();
        result = result * 59 + this.getOverdraftProportionB();
        return result;
    }
}
