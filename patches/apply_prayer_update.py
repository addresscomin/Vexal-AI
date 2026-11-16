from pathlib import Path
base = Path('NamazGuard/app/src/main/java/com/me/blocker')

# Uri tehsil, Baramulla district defaults. Preserve existing custom locations.
p = base / 'Prefs.kt'
s = p.read_text(encoding='utf-8')
s = s.replace('''    fun lat(c: Context): Double = str(c, "lat", "24.8607").toDoubleOrNull() ?: 24.8607
    fun lng(c: Context): Double = str(c, "lng", "67.0011").toDoubleOrNull() ?: 67.0011
''', '''    // Uri, Baramulla (Asia/Kolkata), Karachi calculation / Hanafi Asr.
    fun lat(c: Context): Double {
        val saved = str(c, "lat", "34.0860")
        return (if (saved == "24.8607") "34.0860" else saved).toDoubleOrNull() ?: 34.0860
    }
    fun lng(c: Context): Double {
        val saved = str(c, "lng", "74.0320")
        return (if (saved == "67.0011") "74.0320" else saved).toDoubleOrNull() ?: 74.0320
    }
    fun prayerOffset(c: Context, idx: Int): Int = int(c, "prayer_offset_$idx", 0).coerceIn(-60, 60)
    fun setPrayerOffset(c: Context, idx: Int, minutes: Int) = setInt(c, "prayer_offset_$idx", minutes.coerceIn(-60, 60))
''')
if 'fun prayerOffset' not in s: raise SystemExit('Prefs insertion failed')
p.write_text(s, encoding='utf-8')

# Keep each prayer's existing reminder window length when a minute correction is applied.
p = base / 'PrayerTimes.kt'
s = p.read_text(encoding='utf-8')
s = s.replace('''        val list = (0..4).map { i ->
            Prayer(i, key, starts[i], minOf(ends[i], starts[i] + 90 * 60000L))
        }
''', '''        val list = (0..4).map { i ->
            val correction = Prefs.prayerOffset(c, i) * 60000L
            val adjustedStart = starts[i] + correction
            val adjustedEnd = ends[i] + correction
            Prayer(i, key, adjustedStart, minOf(adjustedEnd, adjustedStart + 90 * 60000L))
        }
''')
if 'adjustedStart' not in s: raise SystemExit('PrayerTimes insertion failed')
p.write_text(s, encoding='utf-8')

# Separate prayer-time correction save: it does not change Strict Mode or phone limits.
p = Path('NamazGuard/app/src/main/java/com/me/blocker/MainActivity.kt')
s = p.read_text(encoding='utf-8')
needle = '''        val etLng = field("Longitude", Prefs.lng(this).toString(), true)
        col.addView(btn("💾 Save") {
'''
insert = '''        val etLng = field("Longitude", Prefs.lng(this).toString(), true)
        col.addView(tv("Prayer time location: Uri, Baramulla (Karachi method · Hanafi Asr)", 13f, false, Color.LTGRAY))
        col.addView(tv("Daily prayer-time correction (minutes)", 17f, true))
        col.addView(tv("0 = automatic calculated time. +5 = 5 minutes later. -5 = 5 minutes earlier. Range: -60 to +60.", 12f, false, Color.LTGRAY))
        val prayerAdjustments = PrayerTimes.NAMES.indices.map { idx ->
            field("${PrayerTimes.NAMES[idx]} correction (min)", Prefs.prayerOffset(this, idx).toString())
        }
        col.addView(btn("🕌 Save prayer timings") {
            val values = prayerAdjustments.map { it.text.toString().toIntOrNull() }
            if (values.any { it == null }) {
                toast("Har prayer correction ke liye valid minutes likho.")
                return@btn
            }
            values.forEachIndexed { idx, value -> Prefs.setPrayerOffset(this, idx, value!!) }
            Scheduler.rescheduleAll(this)
            toast("Prayer timings saved; daily alarms updated.")
            render()
        })
        col.addView(btn("💾 Save") {
'''
if needle not in s: raise SystemExit('MainActivity insertion point missing')
s = s.replace(needle, insert)
p.write_text(s, encoding='utf-8')
