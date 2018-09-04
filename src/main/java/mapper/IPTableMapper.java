package mapper;

import bean.IPTable;

import java.util.List;

public interface IPTableMapper {
    /**
     * 新增代理地址
     *
     * @param ipTable
     * @return status
     */
    int insertIPTable(IPTable ipTable);

    /**
     * 修改代理地址
     *
     * @param ipTable
     * @param id
     * @return status
     */
    int updateIPTable(IPTable ipTable);

    /**
     * 刪除代理地址
     *
     * @param id
     * @return status
     */
    int deleteIPTable(int id);

    /**
     * 根据id查询代理地址信息
     *
     * @param id
     * @return IPTable
     */
    IPTable selectIPTableById(int id);

    /**
     * 查询所有的代理地址信息
     *
     * @return List<IPTable>
     */
    List<IPTable> selectAllIPTable();

    /**
     * 根据使用状态查询代理地址信息
     *
     * @param used
     * @return List<IPTable>
     */
    List<IPTable> selectIPTableByUsed(int used);
}