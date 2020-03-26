package demo;

@SQL
public interface SQLDao {

    @SQL("select * from chris")
    String getCount();
}
