import apiRequest from '../index'
import type { IDataType } from '../types'
import type { IVideoList, IVideoListResult } from './videosType'

enum VideoApi {
  //获取分页视频列表
  getVideoList = '/video/getVideosListByPage',
  //获取视频滚动列表
  getVideoScrollList = '/video/getVideosByCursor'
}

/**
 * 获取分页视频列表
 * @param IVideoList
 */
export function getVideoList(video: IVideoList) {
  return apiRequest.post<IDataType<IVideoListResult>>({
    url: VideoApi.getVideoList,
    data: video
  })
}

//  获取视频滚动列表
export const getVideoScrollList = (cursor: number, size: number) => {
  return apiRequest.post<IDataType>({
    url: VideoApi.getVideoScrollList,
    data: {
      cursor,
      size
    }
  })
}
