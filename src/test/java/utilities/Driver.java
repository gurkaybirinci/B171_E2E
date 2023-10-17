package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Driver {

    private Driver(){

    }

   private static WebDriver driver;
    /**
      * driver'ı başka class'lardan sadece Driver class ismi ile çağırabilmek icin statik yaptık.
      * Henüz bu driver ile ilgili ayarlar yapmadığım için başka class'lar bunu yanlışlıkla kullanmasın diye
        erişimi private yaptık (sadece bu class'in kullanımına açık yaptık).
     */

    public static WebDriver getDriver(){
        if (driver==null){
            // if'i bu metot her calıştığında yeni bir driver oluşturmaması için kullanıyorruz
            // if driver'i kontrol edecek, eğer bir değer ataması yapıldıysa yeni bir driver oluşturmayacak
            switch (ConfigReader.getProperty("browser")) {
                // case'i driver'i istediğimiz browser'da caliştirmak icin kullanıyoruz
                // configuration.properties dosyasında browser olarak ne yazdıysak, tüm testlerimiz o browser'da calışacak
                // browser secimi yapılmadıysa default olarak chrome devreye girecek
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    break;
                default:
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
            }
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        }
        return driver;
    }

    public static void closeDriver(){
        if (driver!=null){
            driver.quit();
        }
        /*
            Burada yeniden null değeri atamamızın sebebi, bir sonraki getDriver metodu çağırışımızda
            yeni değer atayabilmek istememizdir.
        */
        driver=null;
    }
}
