package com.wuhenzhizao.adapter.example.ui

import android.widget.CheckBox
import android.widget.TextView
import com.google.gson.Gson
import com.wuhenzhizao.adapter.*
import com.wuhenzhizao.adapter.example.R
import com.wuhenzhizao.adapter.example.bean.Province
import com.wuhenzhizao.adapter.example.bean.ProvinceList
import com.wuhenzhizao.adapter.example.databinding.FragmentSingleTypeListViewBinding
import com.wuhenzhizao.adapter.extension.getItems

/**
 * Created by liufei on 2017/12/13.
 */
class SingleTypeListViewFragment : BaseFragment<FragmentSingleTypeListViewBinding>() {
    private lateinit var adapter: ListViewAdapter<Province>

    override fun getContentViewId(): Int = R.layout.fragment_single_type_list_view

    override fun initViews() {
        val json = getString(R.string.provinces)
        val list = Gson().fromJson<ProvinceList>(json, ProvinceList::class.java)

        adapter = ListViewAdapter(context, list.provinceList)
                .match(Province::class, R.layout.item_single_type_list_view)
                .holderCreateListener {

                }
                .holderBindListener { holder, position ->
                    val province = adapter.getItem(position)
                    holder.withView<TextView>(R.id.tv, { text = province.name })
                            .withView<CheckBox>(R.id.cb, { isChecked = province.checked })
                }
                .clickListener { holder, position ->
                    val province = adapter.getItem(position)
                    showToast("position $position, ${province.name} clicked")
                    adapter.getItems().forEachIndexed { index, province ->
                        province.checked = (index == position)
                    }
                    adapter.notifyDataSetChanged()
                }
                .longClickListener { holder, position ->
                    val province = adapter.getItem(position)
                    showToast("position $position, ${province.name} long clicked")
                }
                .attach(binding.lv)
    }
}