import edu.univ.erp.auth.UserAuth;
import edu.univ.erp.auth.UserAuth_Access;
import edu.univ.erp.data.auth_database_connection;

import java.sql.Connection;
import java.sql.Timestamp;


public class practice {
    public static void main(String[] args){
        Connection connection = auth_database_connection.getconnection();
        Timestamp ts = Timestamp.valueOf("2025-11-06 12:24:36");
        UserAuth user1 = new UserAuth("mridul","student","qrqwrasf2523","active",ts);
        UserAuth_Access userAuthAccess = new UserAuth_Access();
        userAuthAccess.addUser(user1);

    }
}
