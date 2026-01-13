@Override
public void createBooking(Booking booking) {
    Connection con = null;
    try {
        con = db.getConnection();
        con.setAutoCommit(false);


        String sqlBooking = "INSERT INTO bookings(user_id, room_id, check_in, check_out, total_price) VALUES (?, ?, ?, ?, ?) RETURNING id";

        PreparedStatement st = con.prepareStatement(sqlBooking, Statement.RETURN_GENERATED_KEYS);
        st.setInt(1, booking.getUser().getId());
        st.setInt(2, booking.getRoom().getId());
        st.setDate(3, Date.valueOf(booking.getCheckIn()));
        st.setDate(4, Date.valueOf(booking.getCheckOut()));
        st.setDouble(5, booking.getTotalPrice());
        st.executeUpdate();

        ResultSet rs = st.getGeneratedKeys();
        int bookingId = 0;
        if (rs.next()) {
            bookingId = rs.getInt(1);
        }

        if (booking.getServices() != null && !booking.getServices().isEmpty()) {
            String sqlService = "INSERT INTO booking_services(booking_id, service_id) VALUES (?, ?)";
            PreparedStatement stService = con.prepareStatement(sqlService);

            for (Service service : booking.getServices()) {
                stService.setInt(1, bookingId);
                stService.setInt(2, service.getId());
                stService.addBatch();
            }
            stService.executeBatch();
        }

        con.commit();
    } catch (SQLException e) {
        try { if (con != null) con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
        e.printStackTrace();
    } finally {
        try { if (con != null) con.setAutoCommit(true); con.close(); } catch (SQLException ex) { ex.printStackTrace(); }
    }
}

void main() {
}