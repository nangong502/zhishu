import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.itheima.stock.service.ISysUserService;
import com.itheima.stock.pojo.entity.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = com.itheima.stock.BackendApp.class)
public class demo {
    @Autowired
    ISysUserService iSysUserService;
    @Test
    public void test1(){
        LambdaQueryChainWrapper<SysUser> user = iSysUserService.lambdaQuery()
                .eq(SysUser::getNickName, "超级管理员");
        System.out.println(user);

    }
}
