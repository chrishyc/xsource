package myjava.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Sample {
  @Test
  public void testRange() {
    int result = IntStream.rangeClosed(1, 10).reduce((i, j) -> i * j).getAsInt();
    System.out.println(result);
    List<Integer> list = new LinkedList<>();
    IntStream.rangeClosed(1, 10).forEach(list::add);
  }

  /**
   * https://my.oschina.net/u/3725073/blog/1807970
   * 重复key抛异常
   * value=null抛异常
   */
  @Test
  public void testDuplicate() {
    List<User> userList = new ArrayList<>();
    userList.add(new User(1L, "aaa"));
    userList.add(new User(2L, "bbb"));
    userList.add(new User(3L, "ccc"));
    userList.add(new User(2L, "ddd"));
    userList.add(new User(3L, null));
    Map<Long, String> map = userList.stream()
        .collect(Collectors.toMap(User::getId, User::getUsername, (v1, v2) -> v1));
  }

  public class User {
    private Long id;
    private String name;

    public Long getId() {
      return id;
    }

    public String getUsername() {
      return name;
    }

    public User(Long id, String name) {
      this.id = id;
      this.name = name;
    }
  }

}
