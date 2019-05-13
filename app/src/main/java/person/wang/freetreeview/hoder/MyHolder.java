package person.wang.freetreeview.hoder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.github.johnkil.print.PrintView;


import java.util.ArrayList;
import java.util.List;

import person.wang.freetreeview.R;
import person.wang.freetreeview.model.Children;
import person.wang.freetreeview.treeview.TreeNode;
import person.wang.freetreeview.treeview.WindowUtil;

/**
 * 自定义适配对象
 */
public class MyHolder extends TreeNode.BaseNodeViewHolder<MyHolder.MyItem> {
    private List<Integer> listId;
    private List<Integer> listParentId;
    private int manId=-1;//项目经理id
    private TextView tvValue;
    private PrintView arrowView;
    private TextView tv_percent;//百分比
    private TextView tv_tuser;//负责人
    private RelativeLayout rl_mile;//布局
    private View myView;
    private MyItem myItem;
    private TreeNode myNode;
    public MyHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(final TreeNode node, final MyItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_my_node, null, false);

        this.myItem=value;
        this.myNode=node;
        tvValue = (TextView) view.findViewById(R.id.node_value);
        tvValue.setText(value.mChildren.getTitle());//里程碑名字
        tv_percent= (TextView) view.findViewById(R.id.tv_percent);
        tv_tuser= (TextView) view.findViewById(R.id.tv_uname);
        rl_mile= (RelativeLayout) view.findViewById(R.id.rl_mile);
        myView=view.findViewById(R.id.view);
        arrowView = (PrintView) view.findViewById(R.id.arrow_icon);
        //只有1级里程碑有百分比
        if(value.mChildren.getLevel()==1){
            tv_percent.setText(value.mChildren.getProportion()+"%");
            tv_percent.setVisibility(View.VISIBLE);
        }else {
            tv_percent.setVisibility(View.INVISIBLE);
        }
        tv_tuser.setText(value.mChildren.getPrincipalName());

//设置偏移panding;margin在主题里设置 控制背景色
        rl_mile.setPadding(WindowUtil.dp2px(context,20*(node.getLevel()-1)),0,0,0);
        final ImageView iconView = (ImageView) view.findViewById(R.id.icon);

// 一级二级其他  设置前置图标  一级不用设置 二级是竖线 三级及以后是三角形
        int leave=0;
        Children children=value.mChildren;
        try {
            leave=children.getLevel();
        } catch (Exception e) {
            e.printStackTrace();
        }

        switch (leave){
            case 1:
                if(value.mChildren.getChildren().size()>0){
                    arrowView.setVisibility(View.VISIBLE);
                }
                myView.setVisibility(View.VISIBLE);
                iconView.setVisibility(View.INVISIBLE);
//                iconView.setImageResource(null);
                break;
            case 2:
                iconView.setVisibility(View.VISIBLE);
                iconView.setImageResource(R.mipmap.verticalline);
                break;
            default:
                iconView.setVisibility(View.VISIBLE);
                iconView.setImageResource(R.mipmap.right_triangle);
                break;
        }
        return view;
    }

    @Override
    public void toggle(boolean active) {
        arrowView.setIconText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_up : R.string.ic_keyboard_arrow_down));
    }

    public static class MyItem {
        public Children getmChildren() {
            return mChildren;
        }

        public void setmChildren(Children mChildren) {
            this.mChildren = mChildren;
        }



        private Children mChildren;



        public MyItem(Children children) {
            this.mChildren=children;
        }
    }

    /**
     * 获取当前级别所有父级ID
     */
    private void addParentId(TreeNode treeNode){
        try {
            listParentId.add(((MyItem) (treeNode.getValue())).mChildren.getPrincipalId());
            addParentId(treeNode.getParent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前级别可操作ID
     * @param treeNode
     * @param children
     */
    private void getMileDatas(List<TreeNode> treeNode, Children children) {
        for (int i = 0; i < treeNode.size(); i++) {
            Children mchildren = ((MyHolder.MyItem) (treeNode.get(i).getValue())).getmChildren();
            listId.add(mchildren.getPrincipalId());
            List<Children> one = new ArrayList<>();
            mchildren.setChildren(one);
            for (int j = 0; j < treeNode.get(i).getChildren().size(); j++) {
                Children secChildren = ((MyHolder.MyItem) treeNode.get(i).getChildren().get(j).getValue()).getmChildren();
                List<Children> two = new ArrayList<>();
                secChildren.setChildren(two);
                listId.add(secChildren.getPrincipalId());
                one.add(secChildren);
                getMileDatas(treeNode.get(i).getChildren().get(j).getChildren(),secChildren);
            }
            children.getChildren().add(mchildren);
        }
    }
}
