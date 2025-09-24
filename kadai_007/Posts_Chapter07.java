package kadai_007;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Posts_Chapter07 {

    public static void main(String[] args) {
        Connection con = null;
        Statement statement = null;

        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/java_db",
                    "root",
                    "9490257763387Wwlmnq");

            System.out.println("データベース接続成功");

            statement = con.createStatement();

            // テーブルの作成
            String createTableSql = """
                    CREATE TABLE IF NOT EXISTS posts (
                    post_id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
                    user_id INT(11) NOT NULL,
                    posted_at DATE NOT NULL,
                    post_content VARCHAR(255),
                    likes INT(11) DEFAULT 0
                    );
                    """;
            statement.executeUpdate(createTableSql);
            System.out.println("テーブルの作成に成功しました");

            // データの追加
            String add = """
                    INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES
                    (1003, '2023-02-08', '昨日は徹夜でした・・', 13),
                    (1002, '2023-02-08', 'お疲れ様です！', 12),
                    (1003, '2023-02-09', '今日も頑張ります！', 18),
                    (1001, '2023-02-09', '無理は禁物ですよ！', 17),
                    (1002, '2023-02-10', '明日から連休ですね！', 20);
                    """;

            int rowCnt = statement.executeUpdate(add);
            System.out.println(rowCnt + "件のレコードが追加されました");

            // データの検索
            String search = "SELECT posted_at, post_content, likes FROM posts WHERE user_id = 1002";
            ResultSet result = statement.executeQuery(search);

            while (result.next()) {
                Date postedAt = result.getDate("posted_at");
                String post_content = result.getString("post_content");
                int likes = result.getInt("likes");

                System.out.println("投稿日時=" + postedAt + "／投稿内容=" + post_content + "／いいね数=" + likes);
            }

        } catch (SQLException e) {
            System.out.println("エラー発生：" + e.getMessage());
        } finally {
            if (statement != null) {
                try {statement.close();} catch (SQLException ignore) {}
            }
            if (con != null) {
                try {con.close();} catch (SQLException ignore) {}
            }
        }
    }
}