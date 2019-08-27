#!/bin/bash
sync; echo 1 > /proc/sys/vm/drop_caches && /sbin/swapoff -a && /sbin/swapon -a && printf '%s\n' 'Ram-cache and Swap Cleared'