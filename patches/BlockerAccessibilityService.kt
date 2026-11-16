package com.me.blocker

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent

class BlockerAccessibilityService : AccessibilityService() {
    companion object { @Volatile var currentPkg: String? = null }

    override fun onServiceConnected() {
        super.onServiceConnected()
        if (Prefs.armed(this)) {
            BlockerService.start(this)
            Scheduler.rescheduleAll(this)
        }
    }

    override fun onAccessibilityEvent(e: AccessibilityEvent?) {
        if (e == null) return
        // Window-list changes are emitted during split-screen transitions as well.
        if (e.eventType != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED &&
            e.eventType != AccessibilityEvent.TYPE_WINDOWS_CHANGED) return
        val pkg = e.packageName?.toString() ?: return
        if (pkg == "com.android.systemui") return
        currentPkg = pkg
        val reason = Policy.block(this, pkg)
        if (reason != null) BlockActivity.show(this, reason)
    }

    override fun onInterrupt() {}
}
