/* Nico Giuliani 06/13/2016, 08/30/2018 */

import java.util.Random;

public class Jar {
  public int mCapacity;
  private int mActualAmount;
  public String mItemType;

  public Jar(String itemType, int maxAmount) {
    mItemType = itemType.toLowerCase();
    mCapacity = maxAmount;
  }
  
  public void fill() {
    Random random = new Random();
    mActualAmount = random.nextInt(mCapacity) + 1;
  }

  public int getActualAmount() {
    return mActualAmount;
  }
  
}