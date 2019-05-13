package person.wang.freetreeview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import person.wang.freetreeview.contants.BaseContants;
import person.wang.freetreeview.model.Children;
import person.wang.freetreeview.treeview.Num2CnTool;

public class EditActivity extends AppCompatActivity{
    private ImageView iv_back;//返回
    private Children children;
    private TextView tv_grade;//级别
    private EditText et_milepost;//里程碑
    private EditText et_percent;//百分比
    private EditText et_executor;//执行人

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        children= (Children) getIntent().getSerializableExtra("children");
        initView();
        initData();
        initClck();
    }
    private void initView(){
        tv_grade=findViewById(R.id.tv_grade);
        iv_back=findViewById(R.id.iv_back);
        et_percent=findViewById(R.id.et_percent);
        et_milepost=findViewById(R.id.et_milepost);
        et_executor=findViewById(R.id.et_executor);
    }
    private void initData(){
        tv_grade.setText( Num2CnTool.getGrade((children.getLevel()-1)+"")+"级");
        et_milepost.setText(children.getTitle());
        et_percent.setText(children.getProportion()+"");
        et_executor.setText(children.getPrincipalName());
    if(children.getLevel()>1){
        findViewById(R.id.view_percent).setVisibility(View.GONE);
        findViewById(R.id.ll_percent).setVisibility(View.GONE);
    }
    }
    private void initClck(){
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.tv_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(et_milepost.getText().toString().trim())){
                    Toast.makeText(EditActivity.this,"请输入完整信息",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(et_executor.getText().toString().trim())){
                    Toast.makeText(EditActivity.this,"请输入完整信息",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(children.getLevel()==1){
                    if(TextUtils.isEmpty(et_percent.getText().toString().trim())){
                        Toast.makeText(EditActivity.this,"请输入完整信息",Toast.LENGTH_SHORT).show();
                        return;
                    }
                children.setProportion(Integer.valueOf(et_percent.getText().toString().trim()));
                }
                children.setPrincipalName(et_executor.getText().toString().trim());
                children.setTitle(et_milepost.getText().toString().trim());

                Intent intent=new Intent();
                intent.putExtra("editchildren",children);
                setResult(BaseContants.EDIT,intent);
                finish();


            }
        });
    }
}
