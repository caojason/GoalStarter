package com.example.goalstarterandroidapp;

/**
 * Interface used by MilestoneTouchHelper
 * MilestoneTouchHelper will call the corresponding interface methods when user performs actions on
 * view holder
 * have your view holder implement these methods for MilestoneTouchHelper to work
 */
public interface MilestoneViewHolder {
    /**
     * Called when the view holder is moved
     */
    void onItemSelected();

    /**
     * called when the view holder is done being moved by the user
     */
    void onItemClear();
}
