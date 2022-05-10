package com.yyz.match.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.yyz.match.PersonAdapter
import com.yyz.match.R
import com.yyz.match.base.BaseActivity
import com.yyz.match.entity.PersonBean
import org.angmarch.views.NiceSpinner

/**
 * description none
 * author ez_yang@qq.com
 * date 2022.5.10 下午 4:33
 * version 1.0
 * update none
 **/
class ViewActivity : BaseActivity() {
    override fun getLayoutId() = R.layout.activity_view

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ViewActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var ns: NiceSpinner
    private lateinit var rv: RecyclerView
    private lateinit var adapter: PersonAdapter
    private var list: MutableList<PersonBean> = mutableListOf()
    private lateinit var btn_deleteTable: Button

    override fun initView() {
        ns = findViewById(R.id.ns_view)
        rv = findViewById(R.id.rv_view)
        btn_deleteTable = findViewById(R.id.btn_deleteTable)
        val temp = getTableNameList()
        ns.attachDataSource(temp)
        if (temp.size != 0) {
            list = db.getPersonDao().getPersonByTable(ns.selectedItem.toString())
        }
        adapter = PersonAdapter(list)
        rv.adapter = adapter
        setListener()
    }

    private fun setListener() {
        ns.setOnSpinnerItemSelectedListener { parent, view, position, id ->
            list.clear()
            list.addAll(db.getPersonDao().getPersonByTable(ns.selectedItem.toString()))
            adapter.notifyDataSetChanged()
        }

        adapter.setOnClickListener(object : PersonAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                val builder = AlertDialog.Builder(this@ViewActivity)
                builder.setTitle("删除")
                    .setMessage("是否删除${list[position].parameter}")
                    .setNegativeButton("取消") { dialog, which -> dialog.dismiss() }
                    .setPositiveButton("确定") { dialog, which ->
                        db.getPersonDao().delete(list[position])
                        list.removeAt(position)
                        adapter.notifyDataSetChanged()
                    }
                builder.create().show()
            }

        })

        btn_deleteTable.setOnClickListener {
            if (getTableNameList().size != 0) {
                val builder = AlertDialog.Builder(this@ViewActivity)
                builder.setTitle("删除")
                    .setMessage("是否删除此表格")
                    .setNegativeButton("取消") { dialog, which -> dialog.dismiss() }
                    .setPositiveButton("确定") { dialog, which ->
                        val temptitle = ns.selectedItem.toString()
                        db.getTableDao().delete(db.getTableDao().getTable(temptitle))
                        db.getPersonDao().delete(list)
                        list.clear()
                        val temp = getTableNameList()
                        ns.attachDataSource(temp)
                        if (temp.size != 0) {
                            list.addAll(db.getPersonDao().getPersonByTable(ns.selectedItem.toString()))
                        } else {
                            finish()
                        }
                        adapter.notifyDataSetChanged()
                    }
                builder.create().show()
            }
        }
    }

}