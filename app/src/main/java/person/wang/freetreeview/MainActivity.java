package person.wang.freetreeview;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import person.wang.freetreeview.contants.BaseContants;
import person.wang.freetreeview.dialog.ActionSheetDialog;
import person.wang.freetreeview.hoder.MyHolder;
import person.wang.freetreeview.model.Children;
import person.wang.freetreeview.treeview.AndroidTreeView;
import person.wang.freetreeview.treeview.TreeNode;

public class MainActivity extends AppCompatActivity {
    private ViewGroup container;
    private   Children children;
    private Object myObject;//当前获取的参数
    private TreeNode root;
    private AndroidTreeView tView;
    private TreeNode myTreeNode;//当前点击的数据
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container=findViewById(R.id.container);
        Logger.addLogAdapter(new AndroidLogAdapter());
        children= new GsonBuilder().create().fromJson(BaseContants.json_1,Children.class);
        Logger.e(new Gson().toJson(children));
        initTreeView();
    }

    private void initTreeView(){
        root = TreeNode.root();
        for(int i=0;i<children.getChildren().size();i++){
            TreeNode computerRoot = new TreeNode(new MyHolder.MyItem(children.getChildren().get(i)));
            root.addChildren(computerRoot);
//            pakingData(children.getChildren().get(i),computerRoot);
            if(children.getChildren().get(i).getChildren().size()>0){
                pakingData(children.getChildren().get(i),computerRoot);
            }

        }
        tView = new AndroidTreeView(MainActivity.this, root);
        tView.setDefaultAnimation(false);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        tView.setDefaultViewHolder(MyHolder.class);
        tView.setDefaultNodeClickListener(nodeClickListener);
        tView.setDefaultNodeLongClickListener(nodeLongClickListener);
        tView.setUseAutoToggle(false);
        container.addView(tView.getView());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tView.expandAll();
            }
        },200);
    }
    private void pakingData(Children children,TreeNode treeNode){
        for(int i=0;i<children.getChildren().size();i++){
            TreeNode treeNode1 = new TreeNode(new MyHolder.MyItem(children.getChildren().get(i)));
            treeNode.addChildren(treeNode1);
            pakingData(children.getChildren().get(i),treeNode1);

        }
    }

    private TreeNode.TreeNodeClickListener nodeClickListener = new TreeNode.TreeNodeClickListener() {
        @Override
        public void onClick(final TreeNode node, Object value) {
            myTreeNode=null;
            myTreeNode=node;

            myObject=null;
            myObject=value;
            final MyHolder.MyItem myItem= (MyHolder.MyItem) myObject;

            new ActionSheetDialog(MainActivity.this)
                    .builder()
                    .setCancelable(true)
                    .setCanceledOnTouchOutside(true)
                    .addSheetItem("编辑", ActionSheetDialog.SheetItemColor.Gray,
                            new ActionSheetDialog.OnSheetItemClickListener() {
                                @Override
                                public void onClick(int which) {
                                    Children children=myItem.getmChildren();
                                    Intent intent=new Intent();
                                    intent.setClass(MainActivity.this,EditActivity.class);
                                    intent.putExtra("children",children);
                                    startActivityForResult(intent,BaseContants.EDIT);
                                }
                            })
                    .addSheetItem("新增里程碑", ActionSheetDialog.SheetItemColor.Gray,
                            new ActionSheetDialog.OnSheetItemClickListener() {
                                @Override
                                public void onClick(int which) {

                                    Intent intent=new Intent();
                                    intent.setClass(MainActivity.this,AddMilepostActivity.class);
                                    intent.putExtra("selectChildren",myItem.getmChildren());
                                    startActivityForResult(intent,BaseContants.ADD);

                                }
//
                            })   .addSheetItem("新增子里程碑", ActionSheetDialog.SheetItemColor.Gray,
                    new ActionSheetDialog.OnSheetItemClickListener() {
                        @Override
                        public void onClick(int which) {

                            Intent intent=new Intent();
                            intent.setClass(MainActivity.this,AddMilepostActivity.class);
                            intent.putExtra("selectChildren",myItem.getmChildren());
                            intent.putExtra("isChild",true);//代表添加的是子级
                            startActivityForResult(intent,BaseContants.ADD_CHILD);

                        }
//
                    })  .addSheetItem("删除", ActionSheetDialog.SheetItemColor.Gray,
                    new ActionSheetDialog.OnSheetItemClickListener() {
                        @Override
                        public void onClick(int which) {
                            tView.removeNode(node);
                            tView.expandAll();//很重要
                        }
                    }) .addSheetItem("获取树形json字符串,看LOG", ActionSheetDialog.SheetItemColor.Gray,
                    new ActionSheetDialog.OnSheetItemClickListener() {
                        @Override
                        public void onClick(int which) {

                            children.getChildren().clear();
                            getMileDatas(root.getChildren(),children);
                            String uploadInfo=new Gson().toJson(children);
                            Logger.e(uploadInfo);
                        }
                    })
                    .show();





        }
    };
    private void getMileDatas(List<TreeNode> treeNode, Children children) {
        for (int i = 0; i < treeNode.size(); i++) {
            Children mchildren = ((MyHolder.MyItem) (treeNode.get(i).getValue())).getmChildren();
            List<Children> one = new ArrayList<>();
            mchildren.setChildren(one);
//            if (treeNode.get(i).getChildren().size() > 0) {
            for (int j = 0; j < treeNode.get(i).getChildren().size(); j++) {
                Children secChildren = ((MyHolder.MyItem) treeNode.get(i).getChildren().get(j).getValue()).getmChildren();
//                    mchildren.getChildren().add(secChildren);
                List<Children> two = new ArrayList<>();
                secChildren.setChildren(two);
                one.add(secChildren);

                getMileDatas(treeNode.get(i).getChildren().get(j).getChildren(),secChildren);
            }
//            }
            children.getChildren().add(mchildren);
        }
    }
    private TreeNode.TreeNodeLongClickListener nodeLongClickListener = new TreeNode.TreeNodeLongClickListener() {
        @Override
        public boolean onLongClick(TreeNode node, Object value) {
            return true;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==BaseContants.EDIT&&resultCode==BaseContants.EDIT){
            Children children= (Children) data.getSerializableExtra("editchildren");
            ((MyHolder.MyItem)myObject).setmChildren(children);
            ((TextView)myTreeNode.getViewHolder().getView().findViewById(R.id.node_value)).setText((((MyHolder.MyItem)myObject).getmChildren()).getTitle());
            ((TextView)myTreeNode.getViewHolder().getView().findViewById(R.id.tv_uname)).setText((((MyHolder.MyItem)myObject).getmChildren()).getPrincipalName());
            ((TextView)myTreeNode.getViewHolder().getView().findViewById(R.id.tv_percent)).setText((((MyHolder.MyItem)myObject).getmChildren()).getProportion()+"%");
        }else  if(requestCode==BaseContants.ADD&&resultCode==BaseContants.ADD) {
            Children childrens= (Children) data.getSerializableExtra("addChildren");
            TreeNode newFolder = new TreeNode(new MyHolder.MyItem(childrens));
            tView.addParentNode(myTreeNode, newFolder);
            tView.expandAll();
        }else if(requestCode==BaseContants.ADD_CHILD&&resultCode==BaseContants.ADD_CHILD) {
            Children childrens= (Children) data.getSerializableExtra("addChildren");
            TreeNode newFolder = new TreeNode(new MyHolder.MyItem(childrens));
            tView.addNodeSingle(myTreeNode, newFolder);
            tView.expandAll();
        }
    }
}
