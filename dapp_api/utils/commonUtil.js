let dayjs = require('dayjs')

module.exports = {
    /**
     * 生成期号
     * @param {Integer} gameId 游戏id
     * @param {Integer} end 结束期号
     * @param {Integer} fixed 前缀
     * @param {Integer} minute 间隔时间
     * @returns Array
     */
    async genExpect(gameId, end, fixed, minute) {
        let flag = 1;
        let date = dayjs('2020-01-01 00:00:00');
        let result = [];
        do {
            let offset = date.add(minute, 'minute')

            let num = `${flag}`.padStart(fixed, '0');
            let endTime = offset.format('HH:mm:ss')
            let endTimestamp = await this.parseSecond(offset.format('HH:mm:ss')) 
            if(endTimestamp == 0) {
                endTimestamp = 86400
            }
            result.push({
                gameId,
                num,
                endTime,
                endTimestamp
            })

            date = offset;
            flag+=1;
        } while (flag <= end);
        return result
    },

    /**
     * 时间转换为秒
     * @param {String} str 时间(为HH:mm:ss格式)
     * @returns Integer
     */
    async parseSecond(str) {
        const [hh = '0', mm = '0', ss = '0'] = (str || '0:0:0').split(':');
        const hour = parseInt(hh, 10) || 0;
        const minute = parseInt(mm, 10) || 0;
        const second = parseInt(ss, 10) || 0;
        return (hour*3600) + (minute*60) + (second);
    }
}
