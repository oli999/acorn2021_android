package com.example.step22listview

import android.content.DialogInterface
import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    //값을 나중에 넣어주겠다는 lateinit 예약어를 이용해서 필드를 선언한다.
    lateinit var listView:ListView
    //ListView 에 연결할 모델 (수정가능한 List )
    val list:MutableList<String> = mutableListOf()
    //EditText
    lateinit var inputName:EditText
    //ArrayAdapter
    lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //모델에 sample 데이터 넣어주기
        for(i in 1..20){
            list.add("김구라 $i")
        }
        // ListView 의 참조값을 필드에 저장하기
        listView = findViewById(R.id.listView)
        //ListView 에 연결할 아답타 객체
        adapter = ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_multiple_choice,
            list)
        // ListView 에 아답타 연결하기
        //listView.setAdapter(adapter)
        listView.adapter=adapter // kotlin style !!

        //버튼의 참조값 얻어와서 리스너 등록하기
        val addBtn: Button = findViewById(R.id.addBtn)
        val deleteBtn: Button = findViewById(R.id.deleteBtn)
        addBtn.setOnClickListener(this)
        deleteBtn.setOnClickListener(this)
        //EditText 의 참조값을 필드에 저장
        inputName = findViewById(R.id.inputName)
    }
    // View.OnClickListener 인터페이스를 구현했기 때문에
    override fun onClick(v: View?) {
        /*
            null 을 허용하는 변수나 필드를 참조할때 ? 를 붙여 줘야한다.
            kotlin => v?.id
            java => v.getId()
         */
        when(v?.id){
            R.id.addBtn -> {
                //1. EditText 에 입력한 내용을 읽어와서
                val msg = inputName.text.toString().trim()
                //2. 모델에 추가하고
                list.add(msg)
                //3. 모델의 내용이 바뀌었다고 아답타에 알리고
                adapter.notifyDataSetChanged()
                //4. EditText 의 내용을 삭제하고
                inputName.setText("")
                //5. 키보드를 숨긴다.
                Util.hideKeyboard(this)
            }
            R.id.deleteBtn -> {
                AlertDialog.Builder(this)
                        .setTitle("알림")
                        .setMessage("삭제 하시겠습니까?")
                        .setPositiveButton("네", object:DialogInterface.OnClickListener{
                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                delete() //삭제 진행하기
                            }
                        })
                        .setNegativeButton("아니요", null)
                        .create()
                        .show()
            }
        }
    }
    //선택된 아이템을 삭제하는 메소드
    fun delete(){
        // ListView 의 cell 이 체크 되었는지 안되었는지 여부가
        // 순서대로 들어 있는 희소논리배열(SparseBooleanArray) 얻어내기
        val sba: SparseBooleanArray = listView.checkedItemPositions
        //반복문 돌면서
        for(i in list.size-1 downTo 0){
            // i 번째 cell 이 체크 되었는지 여부를 알아내서
            var isChecked=sba.get(i)
            if(isChecked){ //만일 체크 되었다면
                //모델에서 i 번째 인덱스를 삭제한다.
                list.removeAt(i)
            }
        }
        //모델이 바뀌었다고 아답타에 알리고
        adapter.notifyDataSetChanged()
        //선택된 checkbox 초기화
        listView.clearChoices()
    }
}






