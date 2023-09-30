<script setup lang="ts">
import { ref, watchEffect, reactive } from 'vue'
import { hot, entertainment, society, challenge } from '@/service/test/hot'

const list = reactive({
  hot: [] as any,
  entertainment: [] as any,
  society: [] as any,
  challenge: [] as any
})

watchEffect(async () => {
  list.hot = (await hot()).data.word_list
  list.entertainment = (await entertainment()).data.word_list
  list.society = (await society()).data.word_list
  list.challenge = (await challenge()).data.word_list

  console.log(list.hot)
})
</script>
<template>
  <div class=""></div>
</template>

<style lang="scss" scoped></style>
