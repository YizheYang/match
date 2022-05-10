package com.yyz.match.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.yyz.match.R
import com.yyz.match.base.BaseActivity
import com.yyz.match.entity.PersonBean
import org.angmarch.views.NiceSpinner

/**
 * description none
 * author ez_yang@qq.com
 * date 2022.5.10 上午 12:29
 * version 1.0
 * update none
 **/
class MatchActivity : BaseActivity() {
    override fun getLayoutId() = R.layout.activity_match

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MatchActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var input: EditText
    private lateinit var choose: NiceSpinner
    private lateinit var confirm: Button
    private lateinit var output: TextView
    private lateinit var copy: Button

    @RequiresApi(Build.VERSION_CODES.N)
    override fun initView() {
        input = findViewById(R.id.et_match_input)
        choose = findViewById(R.id.ns_match)
        confirm = findViewById(R.id.btn_match_confirm)
        output = findViewById(R.id.tv_match_output)
        copy = findViewById(R.id.btn_match_copy)
        setListener()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setListener() {
        choose.attachDataSource(getTableNameList())

        confirm.setOnClickListener {
            val s = input.text.toString()
            val waitList = processList(s)
            val table = choose.selectedItem.toString()
            val sqlList = db.getPersonDao().getPersonByTable(table)
            val resultList = listMines(waitList, sqlList)
            val sb = StringBuilder()
            for (result in resultList) {
                sb.append(result.parameter).append("\n")
            }
            output.text = sb.toString()
        }

        copy.setOnClickListener {
            (getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(
                ClipData.newPlainText("label", output.text.toString())
            )
            Toast.makeText(this, "内容已经复制到剪贴板啦", Toast.LENGTH_SHORT).show()
        }
    }

    private fun processList(s: String): MutableList<PersonBean> {
        val list = mutableListOf<PersonBean>()
        for (p in s.split(Regex("\n"))) {
            if (p.isNotEmpty()) {
                val personBean = PersonBean(p)
                list.add(personBean)
            }
        }
        return list
    }

    private fun getTableNameList(): MutableList<String> {
        return db.getTableDao().getAllTable().map { it.chinese }.sorted().toMutableList()
    }

    private fun listMines(
        waitList: MutableList<PersonBean>,
        sqlList: MutableList<PersonBean>
    ): MutableList<PersonBean> {
        val list = mutableListOf<PersonBean>()
        for (sql in sqlList) {
            if (waitList.none { it.parameter == sql.parameter }) {
                list.add(sql)
            }
        }
        return list
    }
}