import com.fileSystemManage.dao.ConnectionUtil;
import com.fileSystemManage.dao.DataBaseUtil;
import com.fileSystemManage.model.FileTreeNode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class FileTreeLoadTest {

    public static void main(String[] args) {
        HashMap<String, FileTreeNode> map = new HashMap();
        ArrayList<FileTreeNode> list = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();
        try {
            PreparedStatement getFilesPs = connection.prepareStatement(DataBaseUtil.GETUSERFILESBYLEFT);
            getFilesPs.setString(1,"1");
            ResultSet rs = getFilesPs.executeQuery();
            while(rs.next()){
                String fileId = rs.getString("file_id");
                String fileName = rs.getString("file_name");
                String parentId = rs.getString("parent_id");
                String parentName = rs.getString("parent_name");
                Integer left = rs.getInt("leftnum");
                Integer right = rs.getInt("rightnum");
                Integer isFolder = rs.getInt("isFolder");
                FileTreeNode node = new FileTreeNode(fileId,fileName,parentId,parentName,left,right,isFolder);
                map.put(fileId,node);
                list.add(node);
            }
            //指定节点之间的关系
            System.out.println(setRelationship(map,list));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static FileTreeNode setRelationship(HashMap<String,FileTreeNode> map,ArrayList<FileTreeNode> list){
        FileTreeNode sonnode = null;
        FileTreeNode parentNode = null;
        for(int i = 1;i< list.size();i++){
            sonnode = list.get(i);
            String parentid = sonnode.getParentId();
            parentNode = map.get(parentid);
            parentNode.add(sonnode);
            System.out.println(sonnode.getFileName()+"被指定为"+parentNode.getFileName()+"的子节点");
        }
        return list.get(0);
    }
}
