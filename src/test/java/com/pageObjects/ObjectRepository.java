package com.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebDriver;

public class ObjectRepository {


    /**
     * @FindBy Annotation -This is a Selenium Page Factory annotation used to locate web elements.
     * Constructor that initializes the WebElements on this page using Selenium's PageFactory.
     * @param driver WebDriver instance used to initialize elements.
     * WebElement representing the username input field,login button,Dashboard page,infra page etc.
     */

    public ObjectRepository(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy (xpath="//input[@id='userid']")
    public  WebElement usernameField;

    @FindBy(xpath = "//input[@id='password']")
    public WebElement passwordField;

    @FindBy(xpath = "//div[@class='login-form__user-id-wrapper login-form__user-id-wrapper--password login-form__fluid-input-label login-form__fluid-input-label--align-top']//button[@name='login']")
    public WebElement loginButton;

    @FindBy(xpath = "//span[@class='cds--list-box__label'][normalize-space()='Dashboard']")
    public WebElement dashBoardPage;

    @FindBy (xpath="//button[@id='main-leftnavbar-network']")
    public  WebElement infrastructureNetwork;

    @FindBy (xpath="//a[@id='navigation.network.clusterNetwork']")
    public  WebElement infrastructureClusterNetwork;

    @FindBy (xpath="//a[@class='cds--link cds--link--inline genesis--ClusterNetworksTableView-description']")
    public  WebElement clusterNetworkCreateButton;

    @FindBy (xpath="//a[@id='aboutPage.leftPanel.apiDocs']")
    public  WebElement apiDocsLink;

    //VPC Locators


}
