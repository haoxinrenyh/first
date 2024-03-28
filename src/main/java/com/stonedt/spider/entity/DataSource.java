package com.stonedt.spider.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 丁祥珂
 * @version V1.0
 * @date 2021/7/26 17:02
 * @Copyright
 */
public class DataSource implements Serializable {
    private Integer id;

    /**
     * 网站名称
     */
    private String websiteName;

    /**
     * 网站url
     */
    private String websiteUrl;

    /**
     * 网站域名
     */
    private String sitedomain;

    /**
     * 网站备注
     */
    private String websiteRemark;

    /**
     * 新网站类形\\\\n1微信，2微博，3政务，4论坛，5新闻，6报刊，7客户端，8网站，9外媒，10视频，11博客，12自媒体，13招投标
     */
    private Integer newWebsiteType;

    /**
     * 网站logo
     */
    private String websiteIco;

    /**
     * 是否是主流网站\\\\n1是，2不是
     */
    private Integer mainstreamFlag;

    /**
     * icp备案信息
     */
    private String icpInfo;

    /**
     * 审核通过时间
     */
    private Date auditTime;

    /**
     * 主办单位名称
     */
    private String sponsor;

    /**
     * 主办单位性质
     */
    private String sponsorNature;

    /**
     * 站点pr值
     */
    private Integer websitePr;

    /**
     * 一级域名
     */
    private String oneDomain;

    /**
     * 二级域名
     */
    private String twoDomain;

    /**
     * 站点说明
     */
    private String websiteExplain;

    /**
     * 网站状态
     */
    private Integer websiteStatus;

    /**
     * 创建人id
     */
    private Integer createUserId;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 智能抓取
     */
    private String urlContainsStr;

    /**
     * 智能抓取
     */
    private String urlShieldStr;

    /**
     * 智能抓取
     */
    private String explainContainsStr;

    /**
     * 智能抓取
     */
    private String explainShieldStr;

    /**
     * 智能抓取
     */
    private Date captureDate;

    /**
     * 智能抓取
     */
    private Integer captureType;

    /**
     * 网站所在市
     */
    private String websiteCity;

    /**
     * 网站所在省
     */
    private String websiteProvince;

    /**
     * 老数据字段
     */
    private String websiteprimaryid;

    /**
     * 老数据字段
     */
    private Integer matchingstatus;

    /**
     * 原网站类型\n1新闻，2财经媒体，3重要市场，4公告，5地区板块分类，6热点概念，7行业分类，8政府机构，9央行动态，10外汇市场，11期货市场，12固定收益市场，13基金理财，14创投并购
     */
    private Integer websiteType;

    /**
     * 自定义的排名
     */
    private Integer siteRank;

    /**
     * 页面上的网站类型
     */
    private String websitetype;

    /**
     * 页面上的网站地区
     */
    private String area;

    /**
     * 百度权重
     */
    private Integer baiduWeight;

    /**
     * 360权重
     */
    private Integer thasWeight;

    /**
     * Alexa排名
     */
    private String alexaRank;

    /**
     * 人工审核（1人工审核，0自动获取）
     */
    private Integer manuallyReview;

    /**
     * 智能匹配（0不开启，1开启，2完成,  3为执行了但没成功）
     */
    private Integer intelligentMatch;

    private Integer rounds;

    //1 不需要补全   2 需要补全
    private Integer messageStatus;

    //创建人
    private String create_user;
    
    //更新时间
    private String update_time;
    
    //更新人
    private String update_user;
    
    //更新人id
    private Integer update_user_id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWebsiteName() {
		return websiteName;
	}

	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
	}

	public String getWebsiteUrl() {
		return websiteUrl;
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public String getSitedomain() {
		return sitedomain;
	}

	public void setSitedomain(String sitedomain) {
		this.sitedomain = sitedomain;
	}

	public String getWebsiteRemark() {
		return websiteRemark;
	}

	public void setWebsiteRemark(String websiteRemark) {
		this.websiteRemark = websiteRemark;
	}

	public Integer getNewWebsiteType() {
		return newWebsiteType;
	}

	public void setNewWebsiteType(Integer newWebsiteType) {
		this.newWebsiteType = newWebsiteType;
	}

	public String getWebsiteIco() {
		return websiteIco;
	}

	public void setWebsiteIco(String websiteIco) {
		this.websiteIco = websiteIco;
	}

	public Integer getMainstreamFlag() {
		return mainstreamFlag;
	}

	public void setMainstreamFlag(Integer mainstreamFlag) {
		this.mainstreamFlag = mainstreamFlag;
	}

	public String getIcpInfo() {
		return icpInfo;
	}

	public void setIcpInfo(String icpInfo) {
		this.icpInfo = icpInfo;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public String getSponsorNature() {
		return sponsorNature;
	}

	public void setSponsorNature(String sponsorNature) {
		this.sponsorNature = sponsorNature;
	}

	public Integer getWebsitePr() {
		return websitePr;
	}

	public void setWebsitePr(Integer websitePr) {
		this.websitePr = websitePr;
	}

	public String getOneDomain() {
		return oneDomain;
	}

	public void setOneDomain(String oneDomain) {
		this.oneDomain = oneDomain;
	}

	public String getTwoDomain() {
		return twoDomain;
	}

	public void setTwoDomain(String twoDomain) {
		this.twoDomain = twoDomain;
	}

	public String getWebsiteExplain() {
		return websiteExplain;
	}

	public void setWebsiteExplain(String websiteExplain) {
		this.websiteExplain = websiteExplain;
	}

	public Integer getWebsiteStatus() {
		return websiteStatus;
	}

	public void setWebsiteStatus(Integer websiteStatus) {
		this.websiteStatus = websiteStatus;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUrlContainsStr() {
		return urlContainsStr;
	}

	public void setUrlContainsStr(String urlContainsStr) {
		this.urlContainsStr = urlContainsStr;
	}

	public String getUrlShieldStr() {
		return urlShieldStr;
	}

	public void setUrlShieldStr(String urlShieldStr) {
		this.urlShieldStr = urlShieldStr;
	}

	public String getExplainContainsStr() {
		return explainContainsStr;
	}

	public void setExplainContainsStr(String explainContainsStr) {
		this.explainContainsStr = explainContainsStr;
	}

	public String getExplainShieldStr() {
		return explainShieldStr;
	}

	public void setExplainShieldStr(String explainShieldStr) {
		this.explainShieldStr = explainShieldStr;
	}

	public Date getCaptureDate() {
		return captureDate;
	}

	public void setCaptureDate(Date captureDate) {
		this.captureDate = captureDate;
	}

	public Integer getCaptureType() {
		return captureType;
	}

	public void setCaptureType(Integer captureType) {
		this.captureType = captureType;
	}

	public String getWebsiteCity() {
		return websiteCity;
	}

	public void setWebsiteCity(String websiteCity) {
		this.websiteCity = websiteCity;
	}

	public String getWebsiteProvince() {
		return websiteProvince;
	}

	public void setWebsiteProvince(String websiteProvince) {
		this.websiteProvince = websiteProvince;
	}

	public String getWebsiteprimaryid() {
		return websiteprimaryid;
	}

	public void setWebsiteprimaryid(String websiteprimaryid) {
		this.websiteprimaryid = websiteprimaryid;
	}

	public Integer getMatchingstatus() {
		return matchingstatus;
	}

	public void setMatchingstatus(Integer matchingstatus) {
		this.matchingstatus = matchingstatus;
	}

	public Integer getWebsiteType() {
		return websiteType;
	}

	public void setWebsiteType(Integer websiteType) {
		this.websiteType = websiteType;
	}

	public Integer getSiteRank() {
		return siteRank;
	}

	public void setSiteRank(Integer siteRank) {
		this.siteRank = siteRank;
	}

	public String getWebsitetype() {
		return websitetype;
	}

	public void setWebsitetype(String websitetype) {
		this.websitetype = websitetype;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Integer getBaiduWeight() {
		return baiduWeight;
	}

	public void setBaiduWeight(Integer baiduWeight) {
		this.baiduWeight = baiduWeight;
	}

	public Integer getThasWeight() {
		return thasWeight;
	}

	public void setThasWeight(Integer thasWeight) {
		this.thasWeight = thasWeight;
	}

	public String getAlexaRank() {
		return alexaRank;
	}

	public void setAlexaRank(String alexaRank) {
		this.alexaRank = alexaRank;
	}

	public Integer getManuallyReview() {
		return manuallyReview;
	}

	public void setManuallyReview(Integer manuallyReview) {
		this.manuallyReview = manuallyReview;
	}

	public Integer getIntelligentMatch() {
		return intelligentMatch;
	}

	public void setIntelligentMatch(Integer intelligentMatch) {
		this.intelligentMatch = intelligentMatch;
	}

	public Integer getRounds() {
		return rounds;
	}

	public void setRounds(Integer rounds) {
		this.rounds = rounds;
	}

	public Integer getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(Integer messageStatus) {
		this.messageStatus = messageStatus;
	}

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}

	public Integer getUpdate_user_id() {
		return update_user_id;
	}

	public void setUpdate_user_id(Integer update_user_id) {
		this.update_user_id = update_user_id;
	}

	public DataSource(Integer id, String websiteName, String websiteUrl, String sitedomain, String websiteRemark,
			Integer newWebsiteType, String websiteIco, Integer mainstreamFlag, String icpInfo, Date auditTime,
			String sponsor, String sponsorNature, Integer websitePr, String oneDomain, String twoDomain,
			String websiteExplain, Integer websiteStatus, Integer createUserId, Date createDate, String urlContainsStr,
			String urlShieldStr, String explainContainsStr, String explainShieldStr, Date captureDate,
			Integer captureType, String websiteCity, String websiteProvince, String websiteprimaryid,
			Integer matchingstatus, Integer websiteType, Integer siteRank, String websitetype2, String area,
			Integer baiduWeight, Integer thasWeight, String alexaRank, Integer manuallyReview, Integer intelligentMatch,
			Integer rounds, Integer messageStatus, String create_user, String update_time, String update_user,
			Integer update_user_id) {
		super();
		this.id = id;
		this.websiteName = websiteName;
		this.websiteUrl = websiteUrl;
		this.sitedomain = sitedomain;
		this.websiteRemark = websiteRemark;
		this.newWebsiteType = newWebsiteType;
		this.websiteIco = websiteIco;
		this.mainstreamFlag = mainstreamFlag;
		this.icpInfo = icpInfo;
		this.auditTime = auditTime;
		this.sponsor = sponsor;
		this.sponsorNature = sponsorNature;
		this.websitePr = websitePr;
		this.oneDomain = oneDomain;
		this.twoDomain = twoDomain;
		this.websiteExplain = websiteExplain;
		this.websiteStatus = websiteStatus;
		this.createUserId = createUserId;
		this.createDate = createDate;
		this.urlContainsStr = urlContainsStr;
		this.urlShieldStr = urlShieldStr;
		this.explainContainsStr = explainContainsStr;
		this.explainShieldStr = explainShieldStr;
		this.captureDate = captureDate;
		this.captureType = captureType;
		this.websiteCity = websiteCity;
		this.websiteProvince = websiteProvince;
		this.websiteprimaryid = websiteprimaryid;
		this.matchingstatus = matchingstatus;
		this.websiteType = websiteType;
		this.siteRank = siteRank;
		websitetype = websitetype2;
		this.area = area;
		this.baiduWeight = baiduWeight;
		this.thasWeight = thasWeight;
		this.alexaRank = alexaRank;
		this.manuallyReview = manuallyReview;
		this.intelligentMatch = intelligentMatch;
		this.rounds = rounds;
		this.messageStatus = messageStatus;
		this.create_user = create_user;
		this.update_time = update_time;
		this.update_user = update_user;
		this.update_user_id = update_user_id;
	}

	public DataSource() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "DataSource [id=" + id + ", websiteName=" + websiteName + ", websiteUrl=" + websiteUrl + ", sitedomain="
				+ sitedomain + ", websiteRemark=" + websiteRemark + ", newWebsiteType=" + newWebsiteType
				+ ", websiteIco=" + websiteIco + ", mainstreamFlag=" + mainstreamFlag + ", icpInfo=" + icpInfo
				+ ", auditTime=" + auditTime + ", sponsor=" + sponsor + ", sponsorNature=" + sponsorNature
				+ ", websitePr=" + websitePr + ", oneDomain=" + oneDomain + ", twoDomain=" + twoDomain
				+ ", websiteExplain=" + websiteExplain + ", websiteStatus=" + websiteStatus + ", createUserId="
				+ createUserId + ", createDate=" + createDate + ", urlContainsStr=" + urlContainsStr + ", urlShieldStr="
				+ urlShieldStr + ", explainContainsStr=" + explainContainsStr + ", explainShieldStr=" + explainShieldStr
				+ ", captureDate=" + captureDate + ", captureType=" + captureType + ", websiteCity=" + websiteCity
				+ ", websiteProvince=" + websiteProvince + ", websiteprimaryid=" + websiteprimaryid
				+ ", matchingstatus=" + matchingstatus + ", websiteType=" + websiteType + ", siteRank=" + siteRank
				+ ", websitetype=" + websitetype + ", area=" + area + ", baiduWeight=" + baiduWeight + ", thasWeight="
				+ thasWeight + ", alexaRank=" + alexaRank + ", manuallyReview=" + manuallyReview + ", intelligentMatch="
				+ intelligentMatch + ", rounds=" + rounds + ", messageStatus=" + messageStatus + ", create_user="
				+ create_user + ", update_time=" + update_time + ", update_user=" + update_user + ", update_user_id="
				+ update_user_id + "]";
	}
    

	
    
    
    
    
    
    
}
