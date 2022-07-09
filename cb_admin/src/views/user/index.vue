<template>
  <div class="app-container">
    <h3 class="title">
      {{ $t('message.msg') }}
    </h3>

    <el-button @click="switchLanguage('zh')">switch-zh</el-button>
    <el-button @click="switchLanguage('en')">switch-en</el-button>
    <template>
      <el-table
        :data="list"
        style="width: 100%"
      >
        <el-table-column
          prop="id"
          label="日期"
          width="180"
        />
        <el-table-column
          prop="username"
          label="姓名"
          width="180"
        />
        <el-table-column
          prop="avatar"
          label="地址"
        />
      </el-table>
    </template>
  </div>
</template>

<script>
import { getUserList } from '@/api/user'

export default {
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'gray',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      list: null,
      listLoading: true,
      params: {
        current: 1,
        size: 10
      }
    }
  },
  created() {
    this.fetchData()
    console.log(this.$i18n.locale)
  },
  methods: {
    fetchData() {
      this.listLoading = true
      getUserList(this.params).then(response => {
        console.log(response)
        this.list = response.data.records
        this.listLoading = false
      })
    },
    switchLanguage(language) {
      this.$store.dispatch('app/setLanguage', language)
      window.location.reload()
    }
  }
}
</script>
