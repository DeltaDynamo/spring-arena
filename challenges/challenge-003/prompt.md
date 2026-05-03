# Global exception handler

Add a global exception handler using `@ControllerAdvice`.

Requirements:
- Handle `IllegalArgumentException` and return `400 Bad Request`.
- Handle generic exceptions and return `500 Internal Server Error`.
- Keep the error payload simple and predictable.
