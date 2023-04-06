import type { ElScrollbar } from 'element-plus'
import { onMounted, type Ref } from 'vue'
import { useDebounceFn } from '@vueuse/core'

const useElScrollbarScroll = (
  scrollbarRef: Ref<InstanceType<typeof ElScrollbar> | null>,
  callback: (scrollTop: number) => void,
  wait = 0
) => {
  const debouncedCallback = useDebounceFn(callback, wait)

  onMounted(() => {
    const scrollbar = scrollbarRef.value
    if (scrollbar) {
      const scrollContent = scrollbar.$el.querySelector('.el-scrollbar__wrap')
      if (scrollContent) {
        scrollContent.addEventListener(
          'scroll',
          (event: { target: HTMLElement }) => {
            const target = event.target as HTMLElement
            debouncedCallback(target.scrollTop)
          }
        )
      }
    }
  })
}

export default useElScrollbarScroll
