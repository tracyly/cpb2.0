package com.fenghuang.caipiaobao.ui.bet

import android.annotation.SuppressLint
import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.baselib.utils.LogUtils
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.home.data.HomeApi
import com.fenghuang.caipiaobao.ui.home.data.LineCheck
import com.fenghuang.caipiaobao.utils.PingUtil
import com.fenghuang.caipiaobao.widget.pop.LiveGiftNumPop
import kotlinx.android.synthetic.main.fragment_bet.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/12/12- 12:21
 * @ Describe
 *
 */

class BetPresenter : BaseMvpPresenter<BetFragment>() {


    @SuppressLint("SetTextI18n")
    fun getUrl() {
        HomeApi.getLotteryUrl {
            onSuccess {
                if (mView.isActive()) {
                    mView.baseUrl = it.betting
                    if (it.bettingArr != null) {
                        mView.baseBetWebView.loadUrl(it.bettingArr!![0])
                        mView.lineList = it.bettingArr
                        val s1 = PingUtil.getAvgRTT(it.bettingArr!![0])
                        mView.tvLineDelay.text = s1.toString() + "ms"
                        if (s1 > 100) {
                            mView.setTextColor(R.id.tvLineDelay, ViewUtils.getColor(R.color.colorYellow))
                        } else {
                            mView.setTextColor(R.id.tvLineDelay, ViewUtils.getColor(R.color.colorGreen))
                        }
                        if (mView.listCheck.isNullOrEmpty()) {
                            mView.listCheck = arrayListOf()
                            for ((index, it) in mView.lineList!!.withIndex()) {
                                val check = if (index == 0) {
                                    LineCheck(it, true)
                                } else LineCheck(it)
                                mView.listCheck?.add(check)
                            }
                        }
                    }
                }
            }
            onFailed { }
        }
    }


}