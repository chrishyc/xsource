import org.junit.Test;

public class SellerCompute {

  @Test
  public void birthdayLetterBall(){
    double birthdayLetterBall = compute(5, 7, 0.3, 0.1, 0.1, 87, 16, 30);
    System.out.println(birthdayLetterBall);
  }

  @Test
  public void roundBall(){
    double birthdayLetterBall = compute(32, 2, 0.35, 0.28, 0.1, 87, 16, 30);
    System.out.println(birthdayLetterBall);
  }

  @Test
  public void paper(){
    double birthdayLetterBall = compute(6, 6, 0.1, 0.28, 0.1, 87, 16, 30);
    System.out.println(birthdayLetterBall);
  }


  private static final double compute(double productPrice, double guoNeiFreight, double productWeight, double bulkWeight,
                                      double packageWeight, double internationalFreight, double registrationFee, double profit) {
    double part1 = productPrice + guoNeiFreight;
    double part2 = ((Math.max(productWeight, bulkWeight) + packageWeight) * internationalFreight + registrationFee) * 1.15;
    double part3 = profit;
    double out = part1 + part2 + part3;
    return out / 7D / 0.85D;
  }
}
