from pathlib import Path

p = Path("NamazGuard/app/src/main/java/com/me/blocker/MainActivity.kt")
s = p.read_text(encoding="utf-8")

s = s.replace(
'''private fun tv(t: String, size: Float = 15f, bold: Boolean = false, color: Int = Color.BLACK) =
        TextView(this).apply {
            text = t; textSize = size; setTextColor(color)
            if (bold) setTypeface(typeface, android.graphics.Typeface.BOLD)
            setPadding(0, 10, 0, 10)
        }''',
'''private fun tv(t: String, size: Float = 15f, bold: Boolean = false, color: Int = Color.WHITE) =
        TextView(this).apply {
            text = t; textSize = size; setTextColor(color)
            if (bold) setTypeface(typeface, android.graphics.Typeface.BOLD)
            setPadding(0, 12, 0, 12)
        }''')

s = s.replace(
'''private fun btn(t: String, f: () -> Unit) = Button(this).apply {
        text = t; isAllCaps = false
        setOnClickListener { f() }
    }''',
'''private fun btn(t: String, f: () -> Unit) = Button(this).apply {
        text = t; isAllCaps = false; textSize = 14f; setTextColor(Color.WHITE)
        background = android.graphics.drawable.GradientDrawable().apply {
            setColor(Color.rgb(42, 91, 112)); cornerRadius = 22f
        }
        setPadding(18, 12, 18, 12)
        setOnClickListener { f() }
    }''')

s = s.replace(
'''val e = EditText(this)
        e.setText(value)''',
'''val e = EditText(this).apply {
            setTextColor(Color.WHITE); setHintTextColor(Color.LTGRAY)
            background = android.graphics.drawable.GradientDrawable().apply {
                setColor(Color.rgb(27, 42, 61)); cornerRadius = 18f
            }
            setPadding(16, 8, 16, 8)
        }
        e.setText(value)''')

s = s.replace(
'''col = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(32, 48, 32, 64) }''',
'''col = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 32, 24, 48)
            background = android.graphics.drawable.GradientDrawable(
                android.graphics.drawable.GradientDrawable.Orientation.TL_BR,
                intArrayOf(Color.rgb(8, 17, 31), Color.rgb(18, 35, 57), Color.rgb(10, 28, 34))
            )
        }''')

s = s.replace('val sv = ScrollView(this)\n        sv.addView(col)',
              'val sv = ScrollView(this).apply { setBackgroundColor(Color.rgb(8, 17, 31)) }\n        sv.addView(col)')

p.write_text(s, encoding="utf-8")
