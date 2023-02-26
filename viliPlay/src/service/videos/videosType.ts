export interface IVideoList {
  page: number
  size: number
  status?: number
}

export interface IVideoListResult {
  id: number
  title: string
  videosCover: string
  description: string
  username: string
  videosTime: string
  videosAddress: string
  uploadTime: string
  playCount: 0
  likeCount: 0
  dislikeCount: 0
  commentCount: 0
  status: 0
}
