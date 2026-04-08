# Grocery Store E-commerce Product Listing

This project is a Spring Boot web application designed to showcase a product listing page with advanced filtering, sorting, and pagination capabilities, typical for an e-commerce platform. It provides a clean, responsive user interface built with Thymeleaf and Bootstrap.

## Features

*   **Product Listing:** Displays a list of products with details.
*   **Price Filtering:** Users can filter products by a minimum and maximum price range.
*   **Dynamic Sorting:** Products can be sorted by various criteria:
    *   Relevance (default, by ID)
    *   Price: Low to High
    *   Price: High to Low
    *   Name: A-Z
    *   Name: Z-A
*   **Pagination:** Efficiently navigate through large sets of products.
*   **Persistent Filters & Sorting:** Filter and sort selections are maintained across pagination.
*   **Responsive Design:** Built with Bootstrap 5 for a mobile-first, responsive layout.
*   **Thymeleaf Templates:** Server-side rendering for dynamic content.
*   **Spring Data JPA:** Simplifies data access and persistence with a database (e.g., H2, MySQL, PostgreSQL).
*   **Spring Security (Basic Setup):** Basic security configuration, with specific endpoints secured (e.g., `/products/**` initially restricted to `MANAGER` role, but can be configured for wider access).

## Technologies Used

*   **Backend:**
    *   Java Development Kit (JDK) 17+
    *   Spring Boot 3.x
    *   Spring Data JPA
    *   Spring Security
    *   Lombok (for boilerplate reduction)
*   **Frontend:**
    *   Thymeleaf (Templating Engine)
    *   Bootstrap 5 (CSS Framework)
    *   JavaScript (Vanilla JS for dynamic UI interactions)
*   **Database:**
    *   H2 Database (in-memory for development, easily configurable for others)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

*   Java Development Kit (JDK) 17 or higher
*   Apache Maven 3.x
*   An IDE like IntelliJ IDEA or Eclipse (recommended)

### Installation

1.  **Clone the repository:**
    ```bash
    git clone <https://github.com/NaverRain/GroceryStore>
    cd MyProject
    ```
2.  **Build the project with Maven:**
    ```bash
    mvn clean install
    ```

### Running the Application

You can run the Spring Boot application in several ways:

1.  **From your IDE:**
    *   Open the project in IntelliJ IDEA or Eclipse.
    *   Locate the main application class (e.g., `MyProjectApplication.java`).
    *   Right-click and select "Run 'MyProjectApplication'".

2.  **From the command line (using Maven):**
    ```bash
    mvn spring-boot:run
    ```
3.  **From the command line (using the JAR file):**
    ```bash
    java -jar target/MyProject-0.0.1-SNAPSHOT.jar # Adjust version as needed
    ```

Once the application starts, it will typically be accessible at `http://localhost:8080`.

## Usage

### Product Listing Page

Navigate to `http://localhost:8080/products` to see the product listing.

### Filtering by Price

On the product listing page, use the "Filter by Price" sidebar:
1.  Enter a "Min Price" and/or "Max Price".
2.  Click the "APPLY" button.
3.  To clear the price filters, click the "Clear all filters" button.

### Sorting Products

Use the "Sort By" dropdown menu on the product listing page:
1.  Click the dropdown to reveal sorting options.
2.  Select your desired sorting method (e.g., "Price: Low to High").
3.  The page will automatically refresh with the new sort order.

### Pagination

Use the pagination controls at the bottom of the product list to navigate between pages. All active filters and sorting preferences will be preserved.

## Project Structure (Key Files)

*   `src/main/java/com/myproject/controller/ProductController.java`: Handles web requests for product listing, filtering, and sorting.
*   `src/main/java/com/myproject/service/ProductService.java`: Business logic for product operations.
*   `src/main/java/com/myproject/repository/ProductRepository.java`: Data access layer for products, utilizing Spring Data JPA and `JpaSpecificationExecutor`.
*   `src/main/java/com/myproject/config/SecurityConfig.java`: Spring Security configuration (important for access control).
*   `src/main/resources/templates/product-list-page.html`: The main Thymeleaf template for displaying products, filters, and sorting.
*   `src/main/resources/templates/fragments/pagination.html`: Reusable Thymeleaf fragment for pagination controls.
*   `src/main/resources/static/css/styles.css`: Custom CSS for styling the application.
*   `src/main/resources/static/js/price-filter.js`: JavaScript for handling client-side filter and sort interactions.