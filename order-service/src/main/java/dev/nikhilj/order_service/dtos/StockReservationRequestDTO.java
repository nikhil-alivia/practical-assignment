package dev.nikhilj.order_service.dtos;

public record StockReservationRequestDTO(Long priceId, int quantityToReserve) {
}
