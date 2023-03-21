<script setup lang="ts">
import {} from 'vue'
import { UserHeader } from '@/components/my'
</script>
<template>
  <div class="user-details">
    <user-header />
  </div>
</template>

<style lang="scss" scoped>
.user-details {
  width: 100%;
  height: 100%;

  .header-right-item-overplay {
    background-color: hsla(0, 0%, 100%, 0.17) !important;
  }
}
</style>
