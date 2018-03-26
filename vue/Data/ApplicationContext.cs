using Microsoft.EntityFrameworkCore;
using Realtors.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Realtors.Data
{
    public class ApplicationContext : DbContext
    {
        public ApplicationContext(DbContextOptions<ApplicationContext> context) : base(context)
        {
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder) => base.OnModelCreating(modelBuilder);

        public DbSet<Realtor> Realtor { get; set; }
        public DbSet<Division> Division { get; set; }
    }
}
