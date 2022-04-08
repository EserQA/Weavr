package io.weavr.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{


    @FindBy(id = "from-email")
    public WebElement emailBox;

    @FindBy(id = "from-password")
    public WebElement passwordBox;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement signInBtn;

    @FindBy(xpath = "//div[@class='alert alert-dismissible alert-danger']")
    public WebElement errorMsg;


}
