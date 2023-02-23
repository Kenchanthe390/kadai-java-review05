package Kadai05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Review05 {
    public static void main(String[] args) {
        // データベース接続と結果取得のための変数宣言
        Connection con = null;
        PreparedStatement pstmt = null;  // SQLを実行するために必要
        ResultSet rs = null;             // SQLの実行結果を格納する変数が必要

        try {
        // ドライバのクラスをJava上で読み込む
        // Class.forName()メソッドの利用時に例外が発生する可能性があるためcatchブロックを追加する
            Class.forName("com.mysql.cj.jdbc.Driver");

        // DBと接続する
        // DBへの接続は、DriverManagerクラスのgetConnection()メソッドを利用する
        // DBへの接続時に例外が発生する可能性があるのでcatchブロックを追加する
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/kadaidb?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "mysqlfkenta@0614"
                    );

        // DBとやりとりする窓口（Statementオブジェクト）を作成する
            String sql = "SELECT * FROM person WHERE id = ?";
            pstmt = con.prepareStatement(sql);

            System.out.print("検索キーワードを入力してください > ");    // キーボード入力を可能にさせる
            int input = Integer.parseInt(keyIn());          // 入力された値はString型なのでint型に変換する

            pstmt.setInt(1, input);     // PreparedStatementオブジェクトの?に値をセット
            rs = pstmt.executeQuery();  // Select文の実行と結果を格納／代入

            System.out.println("while前の文章");    // 検証用に追加

        // 結果を表示する
            while (rs.next()) {
                System.out.println("while中の文章その1");     // 検証用に追加
                String name = rs.getString("name");
                int age = rs.getInt("age");
                System.out.println(name);
                System.out.println(age);

                System.out.println("while中の文章その2");     // 検証用に追加
            }

            System.out.println("while後の文章");            // 検証用に追加

        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバーロード時にエラーが発生しました！");
            e.printStackTrace();

        } catch (SQLException e) {
            System.err.println("DB接続時にエラーが発生しました！");
            e.printStackTrace();

        } finally {

            // 接続した時の逆順で切る（ResultSet,Statement,Connectionの順）
            if (rs != null) {
                try {
                    rs.close();
                } catch(SQLException e) {   // 例外処理が発生する可能性があるため、catchブロックを追加する
                    System.err.println("ResultSetを閉じるときにエラーが発生しました！");
                    e.printStackTrace();
                }
            }

            if (pstmt != null) {
                try {
                    pstmt.close();
                    } catch (SQLException e) {      // 例外処理が発生する可能性があるため、catchブロックを追加する
                    System.err.println("Statementを閉じるときにエラーが発生しました！");
                    e.printStackTrace();
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {      // 例外処理が発生する可能性があるため、catchブロックを追加する
                    System.err.println("DB切断時にエラーが発生しました！");
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * キーボードから入力された値をStringで返す 引数：なし 戻り値：入力された文字列
     */
    private static String keyIn() {
        String result = null;
        try {
            BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
            result = key.readLine();
        } catch (IOException e) {
        }
        return result;
    }
}