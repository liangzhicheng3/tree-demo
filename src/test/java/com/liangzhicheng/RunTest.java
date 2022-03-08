package com.liangzhicheng;

import cn.hutool.json.JSONUtil;
import com.liangzhicheng.modules.entity.Menu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RunTest {

    @Test
    public void testTree(){
        //模拟从数据库查询数据
        List<Menu> menuList = Arrays.asList(
                this.create(1, "根节点", 0),
                this.create(2, "子节点1", 1),
                this.create(3, "子节点1.1", 2),
                this.create(4, "子节点1.2", 2),
                this.create(5, "子节点1.3", 2),
                this.create(6, "根节点2", 1),
                this.create(7, "根节点2.1", 6),
                this.create(8, "根节点2.2", 6),
                this.create(9, "根节点2.1.1", 7),
                this.create(10, "根节点2.2.1", 8),
                this.create(11, "根节点3", 1),
                this.create(12, "根节点3.1", 11));
        //获取父节点
        List<Menu> resultList = menuList.stream().filter(
                menu -> menu.getParentId() == 0
        ).map(
                (menu) -> {
                    menu.setChildList(this.getChildList(menu, menuList));
                    return menu;
                }).collect(Collectors.toList());
        System.out.println(JSONUtil.toJsonStr(resultList));
    }

    private Menu create(Integer id,
                        String name,
                        Integer parentId){
        return new Menu(id, name, parentId);
    }

    /**
     * 递归获取子节点
     * @param root 根节点
     * @param allList 所有节点
     * @return 子节点
     */
    private List<Menu> getChildList(Menu root, List<Menu> allList){
        List<Menu> childList = allList.stream().filter(
                menu -> {
                    return Objects.equals(menu.getParentId(), root.getId());
                }).map(
                        (menu) -> {
                            menu.setChildList(this.getChildList(menu, allList));
                            return menu;
                        }).collect(Collectors.toList());
        return childList;
    }

}
