package Day34;

import Utilities.WebLoader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DatePicker {
    public static void main(String[] args) {
        WebDriver driver = WebLoader.getDriver();

        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@class='demo-frame']")));
        driver.findElement(By.id("datepicker")).click();

        String targetMonth = "March";
        String targetYear = "2027";
        String targetDate = "7";

        while (true) {
            String currentMonth = driver.findElement(By.xpath("//span[@class='ui-datepicker-month']")).getText();
            String currentYear = driver.findElement(By.xpath("//span[@class='ui-datepicker-year']")).getText();

            int currentMonthIndex = getMonthIndex(currentMonth);
            int targetMonthIndex = getMonthIndex(targetMonth);
            int currentYearValue = Integer.parseInt(currentYear);
            int targetYearValue = Integer.parseInt(targetYear);

            if (targetYearValue > currentYearValue ||
                    (targetYearValue == currentYearValue && targetMonthIndex > currentMonthIndex)) {
                driver.findElement(By.xpath("//span[@class='ui-icon ui-icon-circle-triangle-e']")).click();
            } else if (targetYearValue < currentYearValue ||
                    (targetYearValue == currentYearValue && targetMonthIndex < currentMonthIndex)) {
                driver.findElement(By.xpath("//span[@class='ui-icon ui-icon-circle-triangle-w']")).click();
            } else {
                break;
            }
        }

        driver.findElement(By.xpath("//table[@class='ui-datepicker-calendar']//td//a[normalize-space()='" + targetDate + "']")).click();
    }

    private static int getMonthIndex(String month) {
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};

        for (int i = 0; i < months.length; i++) {
            if (months[i].equalsIgnoreCase(month)) {
                return i;
            }
        }
        return -1;
    }
}