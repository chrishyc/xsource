package pattern.chain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 责任链设计模式:
 * https://refactoringguru.cn/design-patterns/chain-of-responsibility/java/example
 * https://refactoringguru.cn/design-patterns/chain-of-responsibility
 *
 * 需求:处理方案经常变更,需要实现开闭原则的方案
 * 方案:引入处理者的概念,每个处理者处理一种业务场景,多个处理者形成处理链路,&&的关系,处理者可以动态增加减少
 * 优点:单一职责,开闭原则
 * 关联:组合模式
 */
public class Demo {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Server server;
    
    private static void init() {
        server = new Server();
        server.register("admin@example.com", "admin_pass");
        server.register("user@example.com", "user_pass");
        
        // All checks are linked. Client can build various chains using the same
        // components.
        Middleware middleware = new ThrottlingMiddleware(2);
        middleware.linkWith(new UserExistsMiddleware(server))
                .linkWith(new RoleCheckMiddleware());
        
        // Server gets a chain from client code.
        server.setMiddleware(middleware);
    }
    
    public static void main(String[] args) throws IOException {
        init();
        
        boolean success;
        do {
            System.out.print("Enter email: ");
            String email = reader.readLine();
            System.out.print("Input password: ");
            String password = reader.readLine();
            success = server.logIn(email, password);
        } while (!success);
    }
}
