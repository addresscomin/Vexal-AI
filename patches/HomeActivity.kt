package com.me.blocker

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.widget.EditText
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/** iOS-inspired home launcher. Block policy is checked both while listing and launching apps. */
class HomeActivity : Activity() {
    private lateinit var grid: GridLayout
    private lateinit var search: EditText
    private var allApps: List<android.content.pm.ResolveInfo> = emptyList()
    private val panel = Color.rgb(24, 36, 56)
    private val muted = Color.rgb(177, 192, 211)
    private val white = Color.WHITE

    override fun onResume() {
        super.onResume()
        if (Prefs.armed(this)) {
            val reason = Policy.ownScreenReason(this)
            if (reason != null) { BlockActivity.show(this, reason); return }
        }
        render()
    }

    private fun rounded(color: Int, radius: Float = 24f) =
        GradientDrawable().apply { setColor(color); cornerRadius = radius }

    private fun text(value: String, size: Float, color: Int, bold: Boolean = false) =
        TextView(this).apply {
            text = value; textSize = size; setTextColor(color)
            if (bold) setTypeface(typeface, Typeface.BOLD)
        }

    private fun render() {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(22, 34, 22, 18)
            background = GradientDrawable(GradientDrawable.Orientation.TL_BR,
                intArrayOf(Color.rgb(8, 17, 31), Color.rgb(18, 35, 57), Color.rgb(10, 28, 34)))
        }
        val date = SimpleDateFormat("EEEE, d MMMM", Locale.getDefault()).format(Date())
        root.addView(text(date, 14f, muted))
        root.addView(text("Good day", 30f, white, true).apply { setPadding(0, 5, 0, 2) })
        root.addView(text("Your focus space", 14f, muted).apply { setPadding(0, 0, 0, 18) })

        val next = PrayerTimes.next(this)
        val prayerText = if (next != null)
            "🕌  NEXT PRAYER  ·  ${PrayerTimes.NAMES[next.idx]}\n" +
                SimpleDateFormat("hh:mm a", Locale.US).format(Date(next.start))
        else "🕌  NAMAZ GUARD"
        root.addView(text(prayerText, 16f, white, true).apply {
            setPadding(18, 16, 18, 16); background = rounded(Color.rgb(34, 67, 76), 22f)
        })

        search = EditText(this).apply {
            hint = "Search apps"; setHintTextColor(muted); setTextColor(white)
            textSize = 15f; singleLine = true; setPadding(18, 4, 18, 4)
            background = rounded(panel, 26f)
        }
        root.addView(search, LinearLayout.LayoutParams(-1, 52).apply { topMargin = 18; bottomMargin = 12 })

        val section = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL }
        section.addView(text("Your apps", 19f, white, true), LinearLayout.LayoutParams(0, -2, 1f))
        section.addView(text("Only allowed apps", 12f, muted))
        root.addView(section)

        val scroll = ScrollView(this)
        grid = GridLayout(this).apply { columnCount = 4; useDefaultMargins = false; setPadding(0, 16, 0, 20) }
        scroll.addView(grid)
        root.addView(scroll, LinearLayout.LayoutParams(-1, 0, 1f))

        val dock = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER
            setPadding(12, 10, 12, 10); background = rounded(Color.rgb(39, 52, 72), 28f)
        }
        addDock(dock, "Phone", "com.google.android.dialer", "com.android.dialer")
        addDock(dock, "Messages", "com.google.android.apps.messaging", "com.android.mms")
        addDock(dock, "Guard", packageName)
        root.addView(dock, LinearLayout.LayoutParams(-1, 70).apply { topMargin = 12 })

        setContentView(root)
        allApps = packageManager.queryIntentActivities(
            Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER), 0
        ).filter { it.activityInfo.packageName != packageName }
            .distinctBy { it.activityInfo.packageName }
            .sortedBy { it.loadLabel(packageManager).toString().lowercase() }

        search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { populate(s?.toString().orEmpty()) }
            override fun afterTextChanged(s: Editable?) {}
        })
        populate("")
    }

    private fun populate(query: String) {
        grid.removeAllViews()
        val apps = allApps.filter {
            Policy.block(this, it.activityInfo.packageName) == null &&
                it.loadLabel(packageManager).toString().contains(query, ignoreCase = true)
        }
        apps.forEach { info ->
            val pkg = info.activityInfo.packageName
            val cell = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL; gravity = Gravity.CENTER; setPadding(4, 8, 4, 14)
                isClickable = true; isFocusable = true
                setOnClickListener {
                    val reason = Policy.block(this@HomeActivity, pkg)
                    if (reason != null) BlockActivity.show(this@HomeActivity, reason)
                    else packageManager.getLaunchIntentForPackage(pkg)?.let { startActivity(it) }
                }
            }
            val icon = ImageView(this).apply {
                setImageDrawable(info.loadIcon(packageManager)); scaleType = ImageView.ScaleType.FIT_CENTER
                background = rounded(Color.rgb(245, 247, 251), 19f); setPadding(10, 10, 10, 10)
            }
            cell.addView(icon, LinearLayout.LayoutParams(58, 58))
            cell.addView(text(info.loadLabel(packageManager).toString(), 11f, white).apply {
                gravity = Gravity.CENTER; maxLines = 2
                ellipsize = android.text.TextUtils.TruncateAt.END; setPadding(0, 6, 0, 0)
            }, LinearLayout.LayoutParams(-1, -2))
            grid.addView(cell, GridLayout.LayoutParams().apply {
                width = 0; height = -2; columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                setMargins(2, 0, 2, 2)
            })
        }
        if (apps.isEmpty()) grid.addView(text("No allowed apps found", 14f, muted).apply {
            gravity = Gravity.CENTER; setPadding(0, 32, 0, 32)
        }, GridLayout.LayoutParams().apply { columnSpec = GridLayout.spec(0, 4) })
    }

    private fun addDock(dock: LinearLayout, label: String, vararg packages: String) {
        val pkg = packages.firstOrNull { it == packageName || packageManager.getLaunchIntentForPackage(it) != null } ?: return
        if (pkg != packageName && Policy.block(this, pkg) != null) return
        val item = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL; gravity = Gravity.CENTER
            setOnClickListener {
                if (pkg == packageName) startActivity(Intent(this@HomeActivity, MainActivity::class.java))
                else {
                    val reason = Policy.block(this@HomeActivity, pkg)
                    if (reason != null) BlockActivity.show(this@HomeActivity, reason)
                    else packageManager.getLaunchIntentForPackage(pkg)?.let { startActivity(it) }
                }
            }
        }
        item.addView(text("●", 22f, white, true)); item.addView(text(label, 10f, white))
        dock.addView(item, LinearLayout.LayoutParams(0, -1, 1f))
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {}
}
