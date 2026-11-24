package org.example.vanillajscrudlab.DTO;

public record UserResponse(
        long id,
        String name,
        String email
) {
}
