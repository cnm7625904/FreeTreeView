package person.wang.freetreeview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import person.wang.freetreeview.contants.BaseContants;
import person.wang.freetreeview.model.Children;
import person.wang.freetreeview.treeview.Num2CnTool;

public class AddMilepostActivity extends AppCompatActivity{
    private Boolean isChild;//是否添加的是子级
    private Children children;
    private TextView tv_grade;
    private EditText et_milepost;
    private LinearLayout ll_percent;
    private View view_percent;
    private EditText et_executor;
    private EditText et_percent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        children= (Children) getIntent().getSerializableExtra("selectChildren");
        isChild=getIntent().getBooleanExtra("isChild",false);
        initView();
        initData();

    }

    private void initData() {
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(children.getLevel()>1){
            view_percent.setVisibility(View.GONE);
            ll_percent.setVisibility(View.GONE);
        }

        if(isChild){
            tv_grade.setText( Num2CnTool.getGrade((children.getLevel())+"")+"级");
            view_percent.setVisibility(View.GONE);
            ll_percent.setVisibility(View.GONE);
        }else {
            tv_grade.setText( Num2CnTool.getGrade((children.getLevel()-1)+"")+"级");
        }
        findViewById(R.id.tv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(et_milepost.getText().toString().trim())){
                    Toast.makeText(AddMilepostActivity.this,"请输入完整信息",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(et_executor.getText().toString().trim())){
                    Toast.makeText(AddMilepostActivity.this,"请输入完整信息",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(children.getLevel()==1&&!isChild){
                    if(TextUtils.isEmpty(et_percent.getText().toString().trim())){
                        Toast.makeText(AddMilepostActivity.this,"请输入完整信息",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    children.setProportion(Integer.valueOf(et_percent.getText().toString().trim()));
                }
                Children childrens=new Children();
                List<Children> childrenList=new ArrayList<>();
                childrens.setPrincipalName(et_executor.getText().toString().trim());
                childrens.setTitle(et_milepost.getText().toString().trim());

                childrens.setChildren(childrenList);
                Intent intent=new Intent();

              if(isChild){
                  childrens.setLevel(children.getLevel()+1);
                  intent.putExtra("addChildren",childrens);
                  setResult(BaseContants.ADD_CHILD,intent);

              }else {
                  childrens.setLevel(children.getLevel());
                  intent.putExtra("addChildren",childrens);
                  setResult(BaseContants.ADD,intent);

              }
                finish();
            }
        });

    }

    private void initView() {
        et_percent=findViewById(R.id.et_percent);
        et_executor=findViewById(R.id.et_executor);
        tv_grade=findViewById(R.id.tv_grade);
        et_milepost=findViewById(R.id.et_milepost);
        ll_percent=findViewById(R.id.ll_percent);
        view_percent=findViewById(R.id.view_percent);
    }
}
