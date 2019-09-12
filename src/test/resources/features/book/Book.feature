Feature: CRUD operations for the book object

  @Functionality
  Scenario: Creating a new book
    Given I want to create a new book
    When I create a new book with title "test", author "test", status "AVAILABLE"
    Then A new book is created title "test", author "test", status "AVAILABLE"

  @Validation
  Scenario: Creating a new book with empty fields
    Given I want to create a new book
    When I create a new book with no title, author "test", status "AVAILABLE"
    Then No book is created and exception thrown

  @Validation
  Scenario: Creating a new book with incorrect fields
    Given I want to create a new book
    When I create a new book with title "test", author "test", and incorrect status "NONE"
    Then No book is created and exception thrown

  @Functionality
  Scenario: Fetching a book
    Given I have created a book title "test", author "test", status "AVAILABLE"
    When I fetch that book
    Then I can see that book with title "test", author "test", status "AVAILABLE"

  @Functionality
  Scenario: Deleting a book
    Given I have created a book title "test", author "test", status "AVAILABLE"
    When I delete that book
    Then That book has been deleted

  @Functionality
  Scenario: Updating the details of a book
    Given I have created a book title "test", author "test", status "AVAILABLE"
    When I want to update the book title to "update"
    Then That book will be updated with title "update", author "test", status "AVAILABLE"

  @Functionality
  Scenario: Borrowing a book
    Given I have created a book title "test", author "test", status "AVAILABLE"
    And A customer with name "Jakob Janssen", number "1234567", email "Jakob@gemail.com", address "123 Norway Lane" wants to borrow this book
    When The customer borrows the book
    Then That book will be updated with the customer's user id and have status "BORROWED"
    And The customer will have a book registered

  @Functionality
  Scenario: Returning a book
    Given I have created a book title "test", author "test", status "AVAILABLE"
    And A customer with name "Jakob Janssen", number "1234567", email "Jakob@gemail.com", address "123 Norway Lane" wants to borrow this book
    When The customer borrows the book
    And The customer returns the book
    Then That book will have status "AVAILABLE"
    And The customer will not have a book registered