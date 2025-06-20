Feature: Login to IBM Cloud Console

  @TC01
  Scenario: Login to IBM Cloud
    Given User is on the IBM Cloud Console login page
    When User enter a valid username
    And User enter a valid password and click on login
    And User should be logged in and see the dashboard

  @TC02
  Scenario: Navigate to the network tab
    Given User is on the IBM Cloud Console login page
    When User enter a valid username
    And User enter a valid password and click on login
    And User click on Network Tab
    And User click on Cluster Network Tab
#    And User click on Cluster Network Create Button
#    And User click on API Docs Link

