package com.spring.ex02.vo;

public class Paging {
	private int pageSize = 3; //한 페이지 당 게시글 수 
	private int blockSize = 3;// 한 블럭 당 페이지 수
	private int listCnt;// 총 게시글 수
	private int pageCnt;// 총 페이지 수
	private int blockCnt;// 총 블럭
	private int curPage;// 현재 페이지
	private int curBlock;// 현재 블럭
	private int startPage;// 시작 페이지
	private int endPage;// 끝 페이지
	private boolean prevPage;// 이전 페이지
	private boolean nextPage;// 다음 페이지
	
	public Paging(int curPage, int listCnt) {
		this.curPage = curPage;
		this.listCnt = listCnt;
		
		this.pageCnt = (int)Math.ceil(listCnt/(double)this.pageSize); //총 페이지 수
		this.blockCnt = (int)Math.ceil(this.pageCnt/(double)this.blockSize); //총 블럭 수
		this.curBlock = (int)Math.ceil(curPage/(double)this.blockSize);
		
		this.startPage = (this.curBlock - 1) * this.blockSize + 1;
		this.endPage = this.curBlock * this.blockSize ;
		
		this.prevPage = this.curBlock==1?false:true;
		this.nextPage = this.endPage >= this.pageCnt?false:true;
		
		if(this.endPage > this.pageCnt) {
			this.endPage = this.pageCnt;
			this.nextPage = false;
		}
	}

	public int getBoardStart() {				//해당 페이지 첫번째 글
		return (this.curPage-1)* this.pageSize;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getBlockSize() {
		return blockSize;
	}

	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	public int getListCnt() {
		return listCnt;
	}

	public void setListCnt(int listCnt) {
		this.listCnt = listCnt;
	}

	public int getPageCnt() {
		return pageCnt;
	}

	public void setPageCnt(int pageCnt) {
		this.pageCnt = pageCnt;
	}

	public int getBlockCnt() {
		return blockCnt;
	}

	public void setBlockCnt(int blockCnt) {
		this.blockCnt = blockCnt;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getCurBlock() {
		return curBlock;
	}

	public void setCurBlock(int curBlock) {
		this.curBlock = curBlock;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrevPage() {
		return prevPage;
	}

	public void setPrevPage(boolean prevPage) {
		this.prevPage = prevPage;
	}

	public boolean isNextPage() {
		return nextPage;
	}

	public void setNextPage(boolean nextPage) {
		this.nextPage = nextPage;
	}
	
	
}
