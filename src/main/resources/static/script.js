document.addEventListener("DOMContentLoaded", function() {
    // Load locations
    fetch('/api/hotels/locations')
        .then(response => response.json())
        .then(data => {
            const locationSelect = document.getElementById('location');
            data.forEach(location => {
                const option = document.createElement('option');
                option.value = location;
                option.textContent = location;
                locationSelect.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Error fetching locations:', error);
        });

    // Load hotels based on selected location
    document.getElementById('location').addEventListener('change', function() {
        const location = this.value;
        fetch(`/api/hotels/locations/${location}/hotels`)
            .then(response => response.json())
            .then(data => {
                const hotelSelect = document.getElementById('hotel');
                hotelSelect.innerHTML = '<option value="" disabled selected>Select a hotel</option>'; // Reset with default option
                data.forEach(hotel => {
                    const option = document.createElement('option');
                    option.value = hotel.id;
                    option.textContent = hotel.name;
                    hotelSelect.appendChild(option);
                });
            })
            .catch(error => {
                console.error('Error fetching hotels:', error);
            });
    });

    // Handle booking form submission
    document.getElementById('booking-form').addEventListener('submit', function(event) {
        event.preventDefault();
        const formData = new FormData(this);
        fetch('/api/bookings/bookRoom', {
            method: 'POST',
            body: formData
        })
        .then(response => response.json())
        .then(data => {
            alert('Room booked successfully!');
            // Clear form or redirect as needed
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error booking room');
        });
    });

    // Handle fetching user bookings
    function fetchUserBookings() {
        fetch('/api/bookings/userBookings')
            .then(response => response.json())
            .then(data => {
                const userBookingsDiv = document.getElementById('userBookings');
                userBookingsDiv.innerHTML = ''; // Clear previous bookings
                data.forEach(booking => {
                    const bookingElement = document.createElement('div');
                    bookingElement.className = 'booking-item';
                    bookingElement.innerHTML = `
                        <strong>Booking ID:</strong> ${booking.id}<br>
                        <strong>Room:</strong> ${booking.roomNumber}<br>
                        <strong>Check-In:</strong> ${booking.checkIn}<br>
                        <strong>Check-Out:</strong> ${booking.checkOut}<br>
                        <strong>Amount:</strong> ${booking.amount}<br>
                        <button class="btn btn-danger btn-sm cancel-btn" data-id="${booking.id}">Cancel</button>
                        <hr>
                    `;
                    userBookingsDiv.appendChild(bookingElement);
                });

                // Add event listeners for cancel buttons
                document.querySelectorAll('.cancel-btn').forEach(button => {
                    button.addEventListener('click', function() {
                        const bookingId = this.getAttribute('data-id');
                        cancelBooking(bookingId);
                    });
                });
            })
            .catch(error => {
                console.error('Error fetching user bookings:', error);
            });
    }

    fetchUserBookings(); // Load user bookings on page load

    // Handle cancel booking
    function cancelBooking(bookingId) {
        fetch('/api/bookings/cancelBooking', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams({ bookingId: bookingId })
        })
        .then(response => response.json())
        .then(data => {
            alert('Booking canceled successfully!');
            fetchUserBookings(); // Refresh user bookings
        })
        .catch(error => {
            console.error('Error canceling booking:', error);
            alert('Error canceling booking');
        });
    }
});
