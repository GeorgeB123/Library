Feature: CRUD operations for the customer object

  @Functionality
  Scenario Outline: Creating a new customer
    Given I want to create a new customer
    When I create a new customer with name "<name>", number "<number>", email "<email>", address "<address>"
    Then A new customer is created name "<name>", number "<number>", email "<email>", address "<address>"
    Examples:
      | name          | number  | email            | address          |
      | Jakob Janssen | 1234567 | Jakob@gemail.com | 123 Norway Lane  |
      | Sunday        | Nope    | test@test.com    | 855 Comeback Way |

  @Validation
  Scenario Outline: Creating a new customer with invalid inputs
    Given I want to create a new customer with description "<description>"
    When I create a new customer with name "<name>", number "<number>", email "<email>", address "<address>"
    Then No new customer is created
    Examples:
      | description     | name        | number  | email            | address          |
      | Name as null    |             | 1234567 | Jakob@gemail.com | 123 Norway Lane  |
      | Number as null  | Jakob Bryan |         | test@test.com    | 855 Comeback Way |
      | Email as null   | Jakob Bryan | Nope    |                  | 855 Comeback Way |
      | Address as null | Jakob Bryan | Nope    | test@test.com    |                  |

  @Functionality
  Scenario Outline: Fetching a customer
    Given I have created a customer name "<name>", number "<number>", email "<email>", address "<address>"
    When I fetch that customer
    Then I can see that customer with name "<name>", number "<number>", email "<email>", address "<address>"
    Examples:
      | name          | number  | email            | address          |
      | Jakob Janssen | 1234567 | Jakob@gemail.com | 123 Norway Lane  |
      | Sunday        | Nope    | test@test.com    | 855 Comeback Way |

  @Validation
  Scenario Outline: Fetching a customer with incorrect parameters
    Given I have created a customer name "<userOneName>", number "<userOneNumber>", email "<userOneEmail>", address "<userOneAddress>"
    And I have created a customer name "<userTwoName>", number "<userTwoNumber>", email "<userTwoEmail>", address "<userTwoAddress>"
    When I fetch that customer with name "<userOneName>", number "<userTwoNumber>" and address "<userOneAddress>"
    Then I receive a null result
    Examples:
      | userOneName   | userOneNumber | userOneEmail     | userOneAddress   | userTwoName     | userTwoNumber | userTwoEmail     | userTwoAddress   |
      | Jakob Janssen | 1234567       | Jakob@gemail.com | 123 Norway Lane  | Jakob Janssenss | 123456534547  | hrtht@gemail.com | 34 Norway Lane   |
      | Sunday        | Nope          | test@test.com    | 855 Comeback Way | Saturday        | Yes           | test@geadfas.com | 855 Comeback Way |

  @Functionality
  Scenario Outline: Deleting a customer
    Given I have created a customer name "<name>", number "<number>", email "<email>", address "<address>"
    When I delete that customer
    Then That customer has been deleted
    Examples:
      | name          | number  | email            | address          |
      | Jakob Janssen | 1234567 | Jakob@gemail.com | 123 Norway Lane  |
      | Sunday        | Nope    | test@test.com    | 855 Comeback Way |

  @Validation
  Scenario Outline: Deleting a customer
    Given I want to delete a customer with "<description>"
    When I try to delete a customer with name "<name>", number "<number>", email "<email>", address "<address>"
    Then That customer can not be deleted as does not exist
    Examples:
      | description           | name          | number  | email            | address         |
      | Non existent customer | Jakob Janssen | 1234567 | Jakob@gemail.com | 123 Norway Lane |

  @Functionality
  Scenario Outline: Updating the details of a customer
    Given I have created a customer name "<name>", number "<number>", email "<email>", address "<address>"
    When I want to update the customer name to "<newName>" and number to "<newNumber>"
    Then That customer will be updated with name "<newName>", number "<newNumber>", email "<email>", address "<address>"
    Examples:
      | name          | number  | email            | address          | newName      | newNumber    |
      | Jakob Janssen | 1234567 | Jakob@gemail.com | 123 Norway Lane  | Jeremy Kyle  | 0978657652   |
      | Sunday        | Nope    | test@test.com    | 855 Comeback Way | Craig Joseph | 423467823452 |

  @Validation
  Scenario Outline: Updating the details of a customer with incorrect values
    Given I have created a customer name "<name>", number "<number>", email "<email>", address "<address>"
    When I want to update the customer name to "<newName>", number to "<newNumber>", email to "<newEmail>", address to "<newAddress>"
    Then That customer will be not be updated to name "<newName>", number "<newNumber>", email "<newEmail>", address "<newAddress>"
    And An error is thrown because "<description>"
    Examples:
      | description     | name          | number  | email            | address          | newName      | newNumber    | newEmail      | newAddress      |
      | Name is null    | Jakob Janssen | 1234567 | Jakob@gemail.com | 123 Norway Lane  |              | 0978657652   | test@test.com | 123 test Street |
      | Number is null  | Sunday        | Nope    | test@test.com    | 855 Comeback Way | Craig Joseph |              | test@test.com | 123 test Street |
      | Email is null   | Sunday        | Nope    | test@test.com    | 855 Comeback Way | Craig Joseph | 423467823452 |               | 123 test Street |
      | Address is null | Sunday        | Nope    | test@test.com    | 855 Comeback Way | Craig Joseph | 423467823452 | test@test.com |                 |