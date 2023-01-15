import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
class SelectOption

{
    Connection con=null;
    PreparedStatement pstmt=null;
    PreparedStatement pstmt1=null;
    ResultSet rset=null;
    Scanner scan=new Scanner(System.in);

 static int selectopt() {
     Scanner scan=new Scanner(System.in);
      int x = 0;
        int i = 0;

        try {

            System.out.println("Insertion Press No-1\t Selection Press No-2\tUpdate Press No-3\tDeletion Press No-4\tExit Press No-5");
            x = scan.nextInt();
            if (x < 1 || x > 5) {
                do {
                    i++;
                    System.out.println("Please Enter Valid Number Between 1 and 5 :");
                    x = scan.nextInt();

                } while (i != 2);


            }

        } catch (InputMismatchException ie) {
            System.out.println("Sorry,It is not valid Input");
        }
        return x;
    }
public void insertion()
{
    System.out.println("no 1 pressed");
    try {
        con = JdbcUtil1.getJdbcConnection();
        String sqlinsert="insert into student(name,age,city,course_id) values(?,?,?,?)";
        if (con != null) {
            pstmt = con.prepareStatement(sqlinsert);
            if(pstmt!=null)
            {
                System.out.println("Enter the Name of the student ");
                String sname=scan.next();


                System.out.println("Enter the Age of the student ");
                int  sage=scan.nextInt();


                System.out.println("Enter the City of the student ");
                String scity=scan.next();


                System.out.println("Enter the Course_id of the student ");
                int courseid=scan.nextInt();

                pstmt.setString(1,sname);
                pstmt.setInt(2,sage);
                pstmt.setString(3,scity);
                pstmt.setInt(4,courseid);

                int rowcount=pstmt.executeUpdate();
                System.out.println("Record Inserted Successfully");


            }
        }
    }catch(SQLException se)
    {
        System.out.println("Invalid data Record insertion Failed");
        //se.printStackTrace();
    }
    catch(Exception e)
    {
        System.out.println("Invalid data Record insertion Failed");
        // e.printStackTrace();
    }
    finally
    {
        try {

            JdbcUtil1.closeresourse(con,pstmt,rset);
            System.out.println("closing the resources...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

public void select()
{
    System.out.println("no 2 pressed");

    try {
        con = JdbcUtil1.getJdbcConnection();
        String sqlselect="select name,age,city,course_id from student where id=?";
        if (con != null) {
            pstmt = con.prepareStatement(sqlselect);
            if(pstmt!=null)
            {

                System.out.println("Enter the student id ");
                int  sid=scan.nextInt();

                pstmt.setInt(1,sid);
                //  System.out.println(sqlselect);
                rset=pstmt.executeQuery();
                System.out.println("Name "+"\t\t"+"Age "+"\t\t"+" city "+"\t\t"+"course_id");
                if(rset.next())
                {
                    System.out.printf(rset.getString(1)+"\t\t"+rset.getInt(2)+"\t\t"+rset.getString(3)+"\t\t"+rset.getInt(4));
                    System.out.println();
                }
                else
                {
                    System.out.println("Record not found");
                }
            }
        }
    }catch(SQLException se)
    {
        //System.out.println("Record not found enter valid student id");
        se.printStackTrace();
    }
    catch(Exception e)
    {
        //System.out.println("Record not found enter valid student id");
        e.printStackTrace();
    }
    finally
    {
        try {

            JdbcUtil.cleanUp(con, pstmt, rset);
            System.out.println("closing the resources...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


public void update()
{
    try {
        con = JdbcUtil1.getJdbcConnection();
        String sqlselect="select name,age,city,course_id from student where id=?";
        String sqlupdate="update student set name=?,age=?,city=?,course_id=? where id=?";
        if (con != null) {
            pstmt = con.prepareStatement(sqlselect);
            pstmt1= con.prepareStatement(sqlupdate);

            if(pstmt!=null)
            {

                System.out.println("Enter the id of the student ");
                int  sid=scan.nextInt();
                pstmt.setInt(1,sid);

                rset=pstmt.executeQuery();
                System.out.println("Name "+"\t\t"+"Age "+"\t\t"+" city "+"\t\t"+"course_id");
                if(rset.next())
                {
                    System.out.printf(rset.getString(1)+"\t\t"+rset.getInt(2)+"\t\t"+rset.getString(3)+"\t\t"+rset.getInt(4));
                    System.out.println();
                    System.out.println("Enter name to update :");
                    String name=scan.next();

                    System.out.println("Enter age to update");
                    int age= scan.nextInt();

                    System.out.println("Enter city to update ");
                    String city=scan.next();

                    System.out.println("Enter Course-id to update ");
                    int course_id=scan.nextInt();
                    if (pstmt1!=null)
                    {
                        pstmt1.setString(1,name);
                        pstmt1.setInt(2,age);
                        pstmt1.setString(3,city);
                        pstmt1.setInt(4,course_id);
                        pstmt1.setInt(5,sid);
                        pstmt1.executeUpdate();
                        System.out.println("Updated Sucessfully");

                    }

                }
                else
                {
                    System.out.println("Record not found");

                }



            }
        }
    }catch(SQLException se)
    {
        se.printStackTrace();
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
    finally
    {
        try {
            JdbcUtil1.closeresourse(con,pstmt,rset);
            pstmt1.close();
            System.out.println("closing the resources...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

public void delete()
{
    try
    {
        con=JdbcUtil1.getJdbcConnection();
        String sqldelete="delete from student where id=?";

        if(con!=null)
        {
            pstmt=  con.prepareStatement(sqldelete);
        }
        if(pstmt!=null) {


            System.out.println("Enter student id to delete the record ");
            int sid = scan.nextInt();
            if(pstmt!=null) {
                pstmt.setInt(1, sid);
                pstmt.executeUpdate();
                System.out.println("Record Deleted sucessfully");
            }
            else
            {
                System.out.println("Record not found ");
            }
        }

    }catch(SQLException se)
    {
        se.printStackTrace();
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
    finally
    {
        try
        {
            JdbcUtil1.closeresourse(con,pstmt,rset);
            System.out.println("Resourses are closed");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }


}
}




public class JdbcAssignBackup {
    Scanner scan=new Scanner(System.in);

    public static void main(String[] args) {

        SelectOption so=new SelectOption();
        JdbcAssignBackup jb=new JdbcAssignBackup();

            int x=0;
       x=so.selectopt();

       int c=0;
        switch (x)
        {

            case 1:
               so.insertion();
                    break;
            case 2:
                so.select();
                    break;
            case 3:
                so.update();
                break;
            case 4:
                so.delete();
               break;

            case 5:

                System.out.println("Thank you");
                break;

        }

    }

}
