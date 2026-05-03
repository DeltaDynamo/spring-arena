# Search users with pagination

Implement `GET /users` with optional `search`, `page`, and `size` query parameters.

Requirements:
- Return a paginated JSON array of users.
- Filter users by name when `search` is provided.
- Default to `page=0` and `size=10` when parameters are missing.
