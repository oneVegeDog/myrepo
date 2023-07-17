import com.fileSystemManage.dao.ConnectionUtil;
import com.fileSystemManage.dao.DataBaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class insertFile {
    public static void main(String[] args) throws SQLException {
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement insertFilePs = connection.prepareStatement(DataBaseUtil.INSERTFILE);
        insertFilePs.setString(1,"c28a59d3-0def-4d79-9386-fe184813a1f1");
        insertFilePs.setString(2, UUID.randomUUID().toString());
        insertFilePs.setString(3,"cai");
        insertFilePs.setString(4,null);
        insertFilePs.setString(5,null);
        insertFilePs.setInt(6,1);
        insertFilePs.setInt(7,2);
        insertFilePs.setInt(8,1);
        insertFilePs.executeUpdate();
        insertFilePs.close();
        connection.close();
    }
}
