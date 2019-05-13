package person.wang.freetreeview.model;

import java.io.Serializable;
import java.util.List;

/**
 * 项目树形对象
 */
public class Children implements Serializable {
    public List<Children> getChildren() {
        return children;
    }

    public void setChildren(List<Children> children) {
        this.children = children;
    }

    /**
     * 此处是实际项目测试对象可以作为参考
     * addTime : 2019-01-22 09:31:22
     * children : [{"addTime":"2019-01-28 15:48:56","children":[],"companyId":1,"complateTime":null,"delayTime":"2019-01-31 23:59:59","delayTimeStamp":1548864000000,"endTime":"2019-01-31 23:59:59","endTimestamp":1548864000000,"id":2601,"isDelay":false,"isOverdue":false,"isWarning":false,"level":1,"name":"需求分析","parentId":2565,"parentPrincipalName":"糖糖","principalId":330,"principalName":"糖糖","projectId":317,"proportion":20,"reportCycle":1,"sortNum":1000,"startTime":"2019-01-28 23:59:59","startTimestamp":1548691199000,"status":2},{"addTime":"2019-01-28 15:48:56","children":[],"companyId":1,"complateTime":null,"delayTime":"2019-01-31 23:59:59","delayTimeStamp":1548864000000,"endTime":"2019-01-31 23:59:59","endTimestamp":1548864000000,"id":2602,"isDelay":false,"isOverdue":false,"isWarning":false,"level":1,"name":"项目开发","parentId":2565,"parentPrincipalName":"糖糖","principalId":331,"principalName":"唐一","projectId":317,"proportion":40,"reportCycle":7,"sortNum":1000,"startTime":"2019-01-28 23:59:59","startTimestamp":1548691199000,"status":2},{"addTime":"2019-01-28 15:48:56","children":[],"companyId":1,"complateTime":null,"delayTime":"2019-01-31 23:59:59","delayTimeStamp":1548864000000,"endTime":"2019-01-31 23:59:59","endTimestamp":1548864000000,"id":2603,"isDelay":false,"isOverdue":false,"isWarning":false,"level":1,"name":"项目测试","parentId":2565,"parentPrincipalName":"糖糖","principalId":332,"principalName":"邓力宾","projectId":317,"proportion":20,"reportCycle":30,"sortNum":1000,"startTime":"2019-01-28 23:59:59","startTimestamp":1548691199000,"status":2},{"addTime":"2019-01-28 15:48:56","children":[],"companyId":1,"complateTime":null,"delayTime":"2019-01-31 23:59:59","delayTimeStamp":1548864000000,"endTime":"2019-01-31 23:59:59","endTimestamp":1548864000000,"id":2604,"isDelay":false,"isOverdue":false,"isWarning":false,"level":1,"name":"项目上线","parentId":2565,"parentPrincipalName":"糖糖","principalId":333,"principalName":"王玺权","projectId":317,"proportion":20,"reportCycle":7,"sortNum":1000,"startTime":"2019-01-28 23:59:59","startTimestamp":1548691199000,"status":2}]
     * companyId : 1
     * complateTime : null
     * delayTime : 2019-01-26 23:59:59
     * delayTimeStamp : 1548518399000
     * endTime : 2019-01-26 23:59:59
     * endTimestamp : 1548518399000
     * id : 2565
     * isDelay : false
     * isOverdue : false
     * isWarning : false
     * level : 0
     * name : test
     * parentId : 0
     * parentPrincipalName : null
     * principalId : 330
     * principalName : 糖糖
     * projectId : 317
     * proportion : 100
     * reportCycle : 1
     * sortNum : 1
     * startTime : 2019-01-22 00:00:00
     * startTimestamp : 1548086400000
     * status : 2
     */




    /**
     * id : 28738
     * level : 0
     * principalId : 1
     * principalName : 糖糖
     * proportion : 100
     * title : 测试
     */

    private List<Children> children;
    private int id;
    private int level;
    private int principalId;
    private String principalName;
    private int proportion;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(int principalId) {
        this.principalId = principalId;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public int getProportion() {
        return proportion;
    }

    public void setProportion(int proportion) {
        this.proportion = proportion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
